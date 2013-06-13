package net.baleful.coinflip.game

import akka.actor._
import net.baleful.coinflip.command._
import scala.concurrent.duration._

object StateMachine {
    sealed trait State

    case object WaitingForPlayers extends State
    case object TakingCalls extends State
    case object EndingRound extends State

    sealed trait Data

    case object Empty extends Data
    case class TimeInfo(val timer: TimerInfo) extends Data
}

class TimerInfo(secondsToWait: Int) {
    val startTime: Long = System.currentTimeMillis()
    val endTime: Long = startTime + (secondsToWait * 1000)

    def remainingTimer(): Duration = Duration(endTime - startTime, "millis")
}

class StateMachine(val config: Config, val game: Game) extends Actor with FSM[StateMachine.State, StateMachine.Data] {
    import StateMachine._

    startWith(WaitingForPlayers, Empty)

    when(WaitingForPlayers) {
        case Event(cmd: JoinGame, _) => {
            if (game.addPlayer(cmd)) {
                val timer: TimerInfo = new TimerInfo(config.getSecondsToTakeCalls())
                goto(TakingCalls) using TimeInfo(timer) forMax timer.remainingTimer
            }
            else {
              stay
            }
        }
        case Event(cmd: GetGameInfo, _) => {
            game.getGameInfo(cmd)

            stay
        }
        case Event(cmd: LeaveGame, _) => {
            game.illegalCommand(cmd)

            stay
        }
        case Event(cmd: CallIt, _) => {
            game.illegalCommand(cmd)

            stay
        }
    }

    when(TakingCalls) {
        case Event(cmd: CallIt, timer: TimeInfo) => {
            game.call(cmd)

            if (game.haveAllPlayersMadeCalls()) {
                goto(EndingRound)
            }
            else {
            	stay using timer forMax timer.timer.remainingTimer()
            }
        }
        case Event(cmd: JoinGame, timer: TimeInfo) => {
            game.addPlayer(cmd)

            stay using timer forMax timer.timer.remainingTimer()
        }
        case Event(cmd: GetGameInfo, timer: TimeInfo) => {
            game.getGameInfo(cmd)

            stay using timer forMax timer.timer.remainingTimer()
        }
        case Event(cmd: LeaveGame, timer: TimeInfo) => {
            if (game.removePlayer(cmd) && game.isGameEmpty()) {
                goto(WaitingForPlayers)
            }
            else {
            	stay using timer forMax timer.timer.remainingTimer()
            }
        }
        case Event(StateTimeout, _) => {
            val timer: TimerInfo = new TimerInfo(config.getSecondsToEndRound())
            goto(EndingRound) using TimeInfo(timer) forMax timer.remainingTimer
        }
    }

    when(EndingRound) {
        case Event(cmd: CallIt, timer: TimeInfo) => {
            game.illegalCommand(cmd)

            stay using timer forMax timer.timer.remainingTimer()
        }
        case Event(cmd: JoinGame, timer: TimeInfo) => {
            game.addPlayer(cmd)

            stay using timer forMax timer.timer.remainingTimer()
        }
        case Event(cmd: GetGameInfo, timer: TimeInfo) => {
            game.getGameInfo(cmd)

            stay using timer forMax timer.timer.remainingTimer()
        }
        case Event(cmd: LeaveGame, timer: TimeInfo) => {
            if (game.removePlayer(cmd) && game.isGameEmpty()) {
                goto(WaitingForPlayers)
            }
            else {
            	stay using timer forMax timer.timer.remainingTimer()
            }
        }
        case Event(StateTimeout, _) => {
            val timer: TimerInfo = new TimerInfo(config.getSecondsToTakeCalls())
            goto(TakingCalls) using TimeInfo(timer) forMax timer.remainingTimer
        }
    }

    onTransition {
        case _ -> TakingCalls => {
            game.startRound()
        }
        case TakingCalls -> EndingRound => {
            game.endRound()
        }
        case _ -> WaitingForPlayers => {
            game.reset()
        }
    }
}