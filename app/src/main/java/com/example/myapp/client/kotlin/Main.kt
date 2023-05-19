import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.AllExercicesQuery
import com.example.CreateOrUpdateExercicesMutation
import com.example.DeleteExercisesMutation
import com.example.type.ExerciseInput


suspend fun main(args: Array<String>) {
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    val apolloClient = ApolloClient.Builder()
        .addHttpHeader("content-type", "application/json")
        .addHttpHeader("Auth", "token") // jwt token
        .serverUrl("http://192.168.0.145:8000/graphql")
        .build()

//     Execute your query. This will suspend until the response is received.
//    var input = Optional.present(ExerciseInput())
//    val response = apolloClient.query(AllExercicesQuery(input)).execute()
//    println("${response.data?.searchExercises?.exercises!!}")
    DeleteExercisesMutation

//    var input2 = ExerciseReqNameInput(name =  "Жопа228", count = Optional.present(15))
//    val response2 = apolloClient.mutation(CreateOrUpdateExercicesMutation(exercise = input2)).execute()
//    println("${response2.data?.createExercise?.exercise!!}")

//    var input3 = Optional.present(ExerciseInput(name =  Optional.present("Качалка")))
//    val response3 = apolloClient.query(AllExercicesQuery(input3)).execute()
//    println("${response3.data?.searchExercises?.exercises!!}")

    var input = Optional.present(ExerciseInput(name = Optional.present("Жопа")))
    val response = apolloClient.query(AllExercicesQuery(input)).execute()
    println("${response.data?.searchExercises?.exercises!!}")



}