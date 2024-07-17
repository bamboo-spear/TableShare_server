package bamboospear.tableshare_server.util.error

class CustomError(val reason: ErrorState): RuntimeException(reason.message)