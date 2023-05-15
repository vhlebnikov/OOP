import static ru.nsu.khlebnikov.GroupConfig.*

tasks {
    task("0", "title", 10, '15-5-2023')
}

oneGroup {
    group("21215")
}

students {
    student("nickname", "name", "sur", "patronymic", "https://url.com")
}

lessons {
    lesson('15-5-2023')
    lesson('15-2-2023')
}

controlMarks {
    controlMark('15-2-2023', "mark")
}

println "$tasksList"
println "$group"
println "$lessonsList"
println "$controlMarksList"
