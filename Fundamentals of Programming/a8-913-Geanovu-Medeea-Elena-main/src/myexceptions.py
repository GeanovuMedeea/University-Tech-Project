class UniqueError(Exception):
    def __init__(self, message):
        super().__init__(message)


class OverlapError(Exception):
    def __init__(self, message):
        super().__init__(message)


class ExistenceError(Exception):
    def __init__(self, message):
        super().__init__(message)


class InvalidPhoneNumber(Exception):
    def __init__(self, message):
        super().__init__(message)


class InvalidTime(Exception):
    def __init__(self, message):
        super().__init__(message)


class InvalidDate(Exception):
    def __init__(self, message):
        super().__init__(message)
