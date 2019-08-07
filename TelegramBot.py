#!/usr/bin/env python
# -*- coding: utf-8 -*-
# This program is dedicated to the public domain under the CC0 license.
#
# THIS EXAMPLE HAS BEEN UPDATED TO WORK WITH THE BETA VERSION 12 OF PYTHON-TELEGRAM-BOT.
# If you're still using version 11.1.0, please see the examples at
# https://github.com/python-telegram-bot/python-telegram-bot/tree/v11.1.0/examples

"""
Simple Bot to reply to Telegram messages.

First, a few handler functions are defined. Then, those functions are passed to
the Dispatcher and registered at their respective places.
Then, the bot is started and runs until we press Ctrl-C on the command line.

Usage:
Basic Echobot example, repeats messages.
Press Ctrl-C on the command line or send a signal to the process to stop the
bot.
"""

import logging

from telegram.ext import Updater, CommandHandler, MessageHandler, Filters

# Enable logging
logging.basicConfig(format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
                    level=logging.INFO)

logger = logging.getLogger(__name__)


# Define a few command handlers. These usually take the two arguments bot and
# update. Error handlers also receive the raised TelegramError object in error.
def start(bot, update):
    """Send a message when the command /start is issued."""
    bot.send_message(chat_id=update.message.chat_id, text="welcome " + update.message.from_user.username)


def help(bot, update):
    """Send a message when the command /help is issued."""
    bot.send_message(chat_id=update.message.chat_id, text='Help!')


def echo(bot, update):
    """Echo the user message."""
    if update.message.from_user.username == "dogu189":
      bot.send_message(chat_id=update.message.chat_id, text="HELLO MY CHILD, I HAVE WOKEN UP")
    elif update.message.from_user.username == "baahadir":
      bot.send_message(chat_id=update.message.chat_id, text="SERIOUSLY BAHADIR, FUCK OFF MY NIGGA")
    else:
      bot.send_message(chat_id=update.message.chat_id, text="YOU ARE NOT ONE OF THE CHOOSEN, GO AWAY ")
def error(bot, update):
    """Log Errors caused by Updates."""
    logger.warning('Update "%s" caused error "%s"', bot, update.error)

def showMax(bot,update):
  bot.send_photo(chat_id=update.message.chat_id,photo='https://vignette.wikia.nocookie.net/ben10/images/e/e9/Max_gwen_10.png/revision/latest/scale-to-width-down/310?cb=20140819185024')

def main():
    """Start the bot."""
    # Create the Updater and pass it your bot's token.
    # Make sure to set use_context=True to use the new context based callbacks
    # Post version 12 this will no longer be necessary
    updater = Updater("988014713:AAEcgRCljLehCjHZjbIJlEw4nWiCioKs4MU")

    # Get the dispatcher to register handlers
    dp = updater.dispatcher

    # on different commands - answer in Telegram
    dp.add_handler(CommandHandler("start", start))
    dp.add_handler(CommandHandler("buyukbabamax",showMax))
    dp.add_handler(CommandHandler("help", help))
    dp.add_handler(CommandHandler("wakeup", echo))

    
    # log all errors
    dp.add_error_handler(error)

    # Start the Bot
    updater.start_polling()

    # Run the bot until you press Ctrl-C or the process receives SIGINT,
    # SIGTERM or SIGABRT. This should be used most of the time, since
    # start_polling() is non-blocking and will stop the bot gracefully.
    updater.idle()


if __name__ == '__main__':
    main()
