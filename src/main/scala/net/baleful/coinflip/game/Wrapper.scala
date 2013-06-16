package net.baleful.coinflip.game

import akka.actor._
import net.baleful.coinflip.command.Command

class Wrapper(val config: Config, val eventHandler: EventHandler, val system: ActorSystem) {
    val game: Game = new Game(eventHandler)

    val actor = system.actorOf(Props(new StateMachine(config, game)))

    def receive(cmd: Command) {
        actor ! cmd
    }
}