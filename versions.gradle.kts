mapOf(
        "kotlinVersion" to "1.4.0",
        "navigationVersion" to "2.3.0",
        "androidXVersion" to "1.3.1",
        "appcompatVersion" to "1.1.0-rc01",
        "materialVersion" to "1.3.0-alpha02",
        "coroutinesVersion" to "1.3.5",
        "archLifecycleVersion" to "2.2.0",
        "retrofitVersion" to "2.9.0",
        "okhttpVersion" to "3.12.3",
        "koinVersion" to "2.1.5",
        "roomVersion" to "2.2.5",
        "constraintVersion" to "2.0.0-rc1"
).forEach { (name, version) ->
    project.extra.set(name, version)
}
