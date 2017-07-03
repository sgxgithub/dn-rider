import io.swagger.models.Scheme

swagger {
    info {
        description = "Move your app forward with the Swagger API Documentation"
        version = "ttn-swagger-1.0.0"
        title = "Swagger API"
    }
    schemes = [Scheme.HTTP]
    consumes = ["application/json"]
}