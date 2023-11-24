from configparser import ConfigParser
from Service.service_board import Service
from AI.ai import AIController
from UI.ui import Console
from GUI.gui import GameGUI


class Settings:
    def __init__(self):
        parser = ConfigParser()
        parser.read(r"C:\Users\Dell\PycharmProjects\pythonProject\a9-913-Geanovu-Medeea-Elena\settings\settings.properties")
        self.ui = None
        game = AIController()
        if parser.get("options", "ui") == "console":
            self.ui = Console(game)
        elif parser.get("options", "ui") == "GUI":
            self.ui = GameGUI(game)

    def get_ui(self):
        return self.ui
