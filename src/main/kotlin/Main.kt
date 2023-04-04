
import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.extensions.api.send.reply
import dev.inmo.tgbotapi.extensions.api.send.replyWithVideo
import dev.inmo.tgbotapi.extensions.api.send.send
import dev.inmo.tgbotapi.extensions.api.telegramBot
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand
import dev.inmo.tgbotapi.requests.abstracts.InputFile
import java.io.File

suspend fun main(args: Array<String>) {
    val token = args.first()
    val fGifsDir = File(args[1])

    val bot = telegramBot(token = token)

    bot.buildBehaviourWithLongPolling {
        println(getMe())

        onCommand("start") {
            reply(it, "Send somebody fuck themself creatively using /gofuck command")
        }

        onCommand("gofuck") {
            val fGifsList = emptyList<File>().toMutableList()
            fGifsDir.listFiles()?.forEach { file ->
                if (file != null && file.isFile) fGifsList.add(file)
            }
            it.replyTo?.let { replyTo ->
                val gif = InputFile.fromFile(fGifsList.random())
                println(gif.filename)
                replyWithVideo(to = replyTo, video = InputFile.fromFile(fGifsList.random()))
            } ?: send(it.chat, "Go fuck yourself, asshole!!!")
        }
    }.join()
}