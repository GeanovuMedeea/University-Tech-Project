class VertexError(Exception):
    """
    Used for signaling errors related to vertex handling. Text is modifiable in ui.
    """
    def __init__(self, message=""):
        super().__init__(message)


class EdgeError(Exception):
    """
    Used for signaling errors related to edge handling. Text is modifiable in ui.
        :return
    """
    def __init__(self, message=""):
        super().__init__(message)
