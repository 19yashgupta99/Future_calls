package org.knoldus.model

import java.util.UUID

case class User(id       : UUID,
                name     : String,
                category : UserType.Value)
