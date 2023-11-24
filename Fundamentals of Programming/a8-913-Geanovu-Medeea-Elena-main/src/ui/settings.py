from configparser import ConfigParser
from src.services.services import Service
from src.ui.console import UI


class Settings:
    def __init__(self):
        parser = ConfigParser()
        parser.read(r"C:\Users\Dell\PycharmProjects\pythonProject\a8-913-Geanovu-Medeea-Elena\src\files\settings.properties")
        self.ui = None
        if parser.get("options", "ui") == "console":
            self.ui = UI
        repo_style = parser.get("options", "repository")
        if repo_style == "memory":
            self.ui = self.ui(Service())

    def get_ui(self):
        return self.ui
