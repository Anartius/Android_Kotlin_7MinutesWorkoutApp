package com.example.a7minutesworkoutapp

object Constants {
    fun getDefaultExerciseList(): MutableList<Exercise> {
        val exerciseList = mutableListOf<Exercise>()

        val jumpingJacks = Exercise(1, "Jumping jacks",
            R.drawable.ic_jumping_jacks)
        exerciseList.add(jumpingJacks)

        val wallSit = Exercise(2,"Wall sit",
            R.drawable.ic_wall_sit)
        exerciseList.add(wallSit)

        val pushUp = Exercise(3, "Push up",
            R.drawable.ic_push_up)
        exerciseList.add(pushUp)

        val abdominalCrunch = Exercise(4, "Abdominal crunch",
            R.drawable.ic_abdominal_crunch)
        exerciseList.add(abdominalCrunch)

        val stepUpOntoChair = Exercise( 5, "Step up onto chair",
            R.drawable.ic_step_up_onto_chair)
        exerciseList.add(stepUpOntoChair)

        val squat = Exercise( 6, "Squat",
            R.drawable.ic_squat)
        exerciseList.add(squat)

        val tricepsDipOnChair = Exercise(7, "Triceps dip on chair",
            R.drawable.ic_triceps_dip_on_chair)
        exerciseList.add(tricepsDipOnChair)

        val plank = Exercise(8, "Plank",
            R.drawable.ic_plank)
        exerciseList.add(plank)

        val highKneesRunningInPlace = Exercise(9, "High knees running in place",
            R.drawable.ic_high_knees_running_in_place)
        exerciseList.add(highKneesRunningInPlace)

        val lunge = Exercise(10, "Lunge",
            R.drawable.ic_lunge)
        exerciseList.add(lunge)

        val pushUpAndRotation = Exercise(11, "Push up amd rotation",
            R.drawable.ic_push_up_and_rotation)
        exerciseList.add(pushUpAndRotation)

        val sidePlank = Exercise(12, "Side plank",
            R.drawable.ic_side_plank)
        exerciseList.add(sidePlank)

        return exerciseList
    }
}