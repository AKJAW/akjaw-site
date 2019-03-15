package akjaw

fun main(args: Array<String>){
    val pjp = ProjectJSONParser("data/projects.json")
    val projects = pjp.getProjects()
    println(projects)
    projects.forEach {
        println(it.translationList)
    }
}