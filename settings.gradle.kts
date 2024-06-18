include("Apache-Kafka-Basics")
include("Wikimedia-Pet-Project")
include("Wikimedia-Pet-Project:Recentchange-Consumer-Microservice")
findProject(":Wikimedia-Pet-Project:Recentchange-Consumer-Microservice")?.name = "Recentchange-Consumer-Microservice"
include("Wikimedia-Pet-Project:Recentchange-Producer-Microservice")
findProject(":Wikimedia-Pet-Project:Recentchange-Producer-Microservice")?.name = "Recentchange-Producer-Microservice"
include("Wikimedia-Pet-Project:Application-Common")
findProject(":Wikimedia-Pet-Project:Application-Common")?.name = "Application-Common"
