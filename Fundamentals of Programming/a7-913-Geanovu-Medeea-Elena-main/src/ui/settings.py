from configparser import ConfigParser
from src.repository.repo import Repository
from src.repository.repo import fileRepository
from src.repository.repo import binFileRepository
from src.services.service import Services
from src.ui.console import UI


class Settings:
    def __init__(self):
        parser = ConfigParser()
        parser.read("files/settings.properties")
        self.ui = UI
        repo_style = parser.get("options", "repository")
        if repo_style == "memory":
            self.ui = self.ui(Services())
        elif repo_style == "textfiles":
            self.ui = self.ui(Services(fileRepository))
        elif repo_style == "binary":
            self.ui = self.ui(Services(binFileRepository))

    def get_ui(self):
        return self.ui

