package com.geekbrains.progect999

import com.geekbrains.progect999.core.HandleDeath
import com.geekbrains.progect999.subscription.SubscriptionRepresentative
import org.junit.Before
import org.junit.Test

class SubscriptionRepresentativeTest {//todo
    private lateinit var representative: SubscriptionRepresentative

    @Before
    fun setup(){
       // representative = SubscriptionRepresentative.Base(FakeHandleDeath.Base())
    }
    @Test
    fun test(){
       // representative.init(false)
    }
}
private interface FakeHandleDeath : HandleDeath{
    fun killProcess()


    class Base : FakeHandleDeath {
        private var deathHappened = true
        override fun killProcess() {
            deathHappened = true
        }

        override fun firstOpening() {
            deathHappened = false
        }

        override fun didDeathHappen(): Boolean {
            return deathHappened
        }

        override fun deathHandled() {
            deathHappened = false
        }
    }
}