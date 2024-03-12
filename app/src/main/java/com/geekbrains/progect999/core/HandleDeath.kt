package com.geekbrains.progect999.core

/**
 * обработка смерти
 */
interface HandleDeath {
    /**
     * первое открытие
     */
    fun firstOpening()
    /**
     * была ли смерть
     */
    fun didDeathHappen():Boolean
    /**
     * смерть обработана
     */
    fun deathHandled()
    class Base(): HandleDeath {
        /**
         * смерть случилась
         */
        private var deathHappened = true
        override fun firstOpening() {
           deathHappened = false
        }

        override fun didDeathHappen():Boolean {
            return deathHappened
        }

        override fun deathHandled() {
            deathHappened = false
        }

    }
}