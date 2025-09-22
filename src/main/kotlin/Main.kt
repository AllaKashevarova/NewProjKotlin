fun main(args: Array<String>) {
//    val name = "Kotlin" // immutable variable
//    var count = 1        // mutable variable
//    println("Hello, $name! Run #$count")
//
//    count += 1
//    println("Next run: #$count")

    val greeter = Basics()
    val message = greeter.greetings("Lola")
    println(message)
}
