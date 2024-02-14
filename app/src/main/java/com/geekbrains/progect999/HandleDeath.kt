package com.geekbrains.progect999

interface HandleDeath {
    fun firstOpening()
    fun wasDeathHappened():Boolean
    fun deathHandled()
    class Base():HandleDeath{
        /**
         * смерть случилась
         */
        private var deathHappened = true

        /**
         * первое открытие
         */
        override fun firstOpening() {
           deathHappened = false
        }
        /**
         * была ли смерть
         */
        override fun wasDeathHappened():Boolean {
            return deathHappened
        }
        /**
         * смерть обработана
         */
        override fun deathHandled() {
            deathHappened = false
        }

    }
}