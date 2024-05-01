package com.geekbrains.progect999

import com.geekbrains.progect999.core.ClearRepresentative
import com.geekbrains.progect999.core.HandleDeath
import com.geekbrains.progect999.core.Representative
import com.geekbrains.progect999.core.RunAsync
import com.geekbrains.progect999.core.UiObserver
import com.geekbrains.progect999.main.Navigation
import com.geekbrains.progect999.main.Screen
import com.geekbrains.progect999.subscription.domain.SubscriptionInteractor
import com.geekbrains.progect999.subscription.domain.SubscriptionResult
import com.geekbrains.progect999.subscription.presentation.EmptySubscriptionObserver
import com.geekbrains.progect999.subscription.presentation.SaveAndRestoreSubscriptionUiState
import com.geekbrains.progect999.subscription.presentation.SubscriptionObservable
import com.geekbrains.progect999.subscription.presentation.SubscriptionObserver
import com.geekbrains.progect999.subscription.presentation.SubscriptionRepresentative
import com.geekbrains.progect999.subscription.presentation.SubscriptionUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
//fixme after loading representative refactoring
class SubscriptionRepresentativeTest {
    private lateinit var representative: SubscriptionRepresentative
    private lateinit var observable: FakeObservable
    private lateinit var clear: FakeClear
    private lateinit var interactor: FakeInteractor
    private lateinit var navigation: FakeNavigation
    private lateinit var handleDeath: FakeHandleDeath
    private lateinit var runAsync : FakeRunAsync

    @Before
    fun setup(){
        observable = FakeObservable.Base()
        clear = FakeClear.Base()
        interactor = FakeInteractor.Base()
        navigation = FakeNavigation.Base()
        handleDeath = FakeHandleDeath.Base()
        runAsync = FakeRunAsync.Base()
        representative = SubscriptionRepresentative.Base(
            runAsync,
            handleDeath,
            observable,
            clear,
            interactor,
            navigation,
            FakeMapper(observable))
    }
    @Test
    fun main_scenario(){
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        handleDeath.checkFirstOpeningCalled(1)
        observable.checkUiState(SubscriptionUiState.Initial)

        val callBack = object : SubscriptionObserver {
            override fun update(data: SubscriptionUiState) = Unit
        }
        representative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        representative.subscribe()
        observable.checkUiState(SubscriptionUiState.Loading)
        interactor.checkSubscribeCalledTimes(1)
        runAsync.pingResult()
        observable.checkUiState(SubscriptionUiState.Success)

        representative.observed()
        observable.checkClearCalled()

        representative.finish()
        clear.checkClearCalledWith(SubscriptionRepresentative::class.java)
        navigation.checkUpdated(Screen.Dashboard)

        representative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptySubscriptionObserver)
    }

    @Test
    fun test_save_and_restore(){
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        handleDeath.checkFirstOpeningCalled(1)
        observable.checkUiState(SubscriptionUiState.Initial)
        observable.checkUpdateCalledCount(1)

        val callBack = object : SubscriptionObserver {
            override fun update(data: SubscriptionUiState) = Unit
        }
        representative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        representative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptySubscriptionObserver)
        representative.saveState(saveAndRestore)

        representative.init(saveAndRestore)
        handleDeath.checkFirstOpeningCalled(1)
        observable.checkUpdateCalledCount(1)
    }

    @Test
    fun test_death_after_loading(){
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        handleDeath.checkFirstOpeningCalled(1)
        observable.checkUiState(SubscriptionUiState.Initial)

        val callBack = object : SubscriptionObserver {
            override fun update(data: SubscriptionUiState) = Unit
        }
        representative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        representative.subscribe()
        observable.checkUiState(SubscriptionUiState.Loading)
        interactor.checkSubscribeCalledTimes(1)

        representative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptySubscriptionObserver)
        representative.saveState(saveAndRestore)
        //region death happening here
      setup()
        //endregion
        representative.init(saveAndRestore)
        handleDeath.checkFirstOpeningCalled(0)
        observable.checkUiState(SubscriptionUiState.Empty)
        observable.checkUpdateCalledCount(0)
        interactor.checkSubscribeCalledTimes(1)
    }
    @Test
    fun test_death_after_success(){
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        handleDeath.checkFirstOpeningCalled(1)
        observable.checkUiState(SubscriptionUiState.Initial)

        val callBack = object : SubscriptionObserver {
            override fun update(data: SubscriptionUiState) = Unit
        }
        representative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        representative.subscribe()
        observable.checkUiState(SubscriptionUiState.Loading)
        interactor.checkSubscribeCalledTimes(1)
        runAsync.pingResult()
        observable.checkUiState(SubscriptionUiState.Success)
        representative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptySubscriptionObserver)
        representative.saveState(saveAndRestore)
        //region death happening here
        setup()
        //endregion
        representative.init(saveAndRestore)
        handleDeath.checkFirstOpeningCalled(0)
        observable.checkUiState(SubscriptionUiState.Success)
        observable.checkUpdateCalledCount(1)
        interactor.checkSubscribeCalledTimes(0)
    }

    @Test
    fun test_death_after_success_observed(){
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        handleDeath.checkFirstOpeningCalled(1)
        observable.checkUiState(SubscriptionUiState.Initial)

        val callBack = object : SubscriptionObserver {
            override fun update(data: SubscriptionUiState) = Unit
        }
        representative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        representative.subscribe()
        observable.checkUiState(SubscriptionUiState.Loading)
        interactor.checkSubscribeCalledTimes(1)
        runAsync.pingResult()
        observable.checkUiState(SubscriptionUiState.Success)
        representative.observed()
        observable.checkClearCalled()
        representative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptySubscriptionObserver)
        representative.saveState(saveAndRestore)

        //region death happening here
        setup()
        //endregion

        representative.init(saveAndRestore)
        handleDeath.checkFirstOpeningCalled(0)
        observable.checkUiState(SubscriptionUiState.Empty)
        observable.checkUpdateCalledCount(0)
        interactor.checkSubscribeCalledTimes(0)
    }

    @Test
    fun test_cannot_go_back(){
        val callBack = object : SubscriptionObserver {
            override fun update(data: SubscriptionUiState) = Unit
        }
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        representative.startGettingUpdates(callBack)
        representative.subscribe()
        representative.comeback()
        runAsync.checkClearCalledTimes(0)
    }
    @Test
    fun test_can_go_back(){
        val callBack = object : SubscriptionObserver {
            override fun update(data: SubscriptionUiState) = Unit
        }
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        representative.startGettingUpdates(callBack)
        representative.subscribe()
        runAsync.pingResult()
        representative.comeback()
        runAsync.checkClearCalledTimes(1)
    }
}
private interface FakeSaveAndRestore : SaveAndRestoreSubscriptionUiState.Mutable{

    class Base : FakeSaveAndRestore {
        private var state = mutableListOf<SubscriptionUiState>()
        override fun isEmpty() = state.isEmpty()

        override fun save(data: SubscriptionUiState) {
            state.add(data)
        }

        override fun restore(): SubscriptionUiState {
            return state.last()
        }
    }
}
private interface FakeNavigation : Navigation.Update {
    fun checkUpdated(screen: Screen)
    class Base : FakeNavigation{
        private var updateCalledWithScreen : Screen = Screen.Empty
        override fun checkUpdated(screen: Screen) {
            assertEquals(screen,updateCalledWithScreen)
        }

        override fun update(data: Screen) {
            updateCalledWithScreen = data
        }

    }
}
private interface FakeClear : ClearRepresentative{
    fun checkClearCalledWith(clazz: Class<out Representative<*>>)
    class Base : FakeClear{
        private var clearCalledClazz : Class<out Representative<*>>? = null
        override fun checkClearCalledWith(clazz: Class<out Representative<*>>) {
            assertEquals(clazz,clearCalledClazz)
        }

        override fun clear(clazz: Class<out Representative<*>>) {
            clearCalledClazz = clazz
        }

    }
}
private interface FakeInteractor : SubscriptionInteractor{
    fun checkSubscribeCalledTimes(times: Int)
    class Base : FakeInteractor{
        private var cashCallback : () -> Unit = {}
        private var subscribeCalledCount = 0
        override suspend fun subscribe(): SubscriptionResult {
            subscribeCalledCount ++
            return SubscriptionResult.NoDataYet
        }

        override suspend fun subscribeInternal(): SubscriptionResult {
            return SubscriptionResult.Success
        }

        override fun checkSubscribeCalledTimes(times: Int) {
            assertEquals(times,subscribeCalledCount)
        }
    }
}
private interface FakeRunAsync : RunAsync{
    fun checkClearCalledTimes(times: Int)
    fun pingResult()
    class Base : FakeRunAsync {
        private var cashedBlock : (Any) -> Unit = {}
        private var cashed : Any = Unit
        private var clearCalledTimes =0
        override fun checkClearCalledTimes(times: Int) {
            assertEquals(times,clearCalledTimes)
        }

        override fun pingResult() {
            cashedBlock.invoke(cashed)
        }

        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            backgroundBlock: suspend () -> T,
            uiBlock: (T) -> Unit
        ) {
            runBlocking {
                cashed = backgroundBlock.invoke()
                cashedBlock = uiBlock as (Any) -> Unit
            }
        }

        override suspend fun <T : Any> runAsync(
            backgroundBlock: suspend () -> T,
            uiBlock: (T) -> Unit
        ) {
            uiBlock.invoke(backgroundBlock.invoke())
        }

        override fun clear() {
            clearCalledTimes++
        }

    }
}
private interface FakeObservable : SubscriptionObservable{

    fun checkUpdateCalledCount(times: Int)
    fun checkClearCalled()
    fun checkUiState(uiState: SubscriptionUiState)
    fun checkUpdateObserverCalled(observer: SubscriptionObserver)
    class Base : FakeObservable{
        private var clearCalled = false
        override fun checkClearCalled() {
            assertEquals(true,clearCalled)
            clearCalled = false
        }
        override fun clear() {
            clearCalled = true
            cashe = SubscriptionUiState.Empty
        }
        private var updateCalledCount = 0
        private var cashe :SubscriptionUiState = SubscriptionUiState.Empty
        override fun update(data: SubscriptionUiState) {
            cashe = data
            updateCalledCount++
        }

        override fun checkUpdateCalledCount(times: Int) {
            assertEquals(times,updateCalledCount)
        }
        override fun checkUiState(uiState: SubscriptionUiState) {
            assertEquals(uiState,cashe)
        }

        override fun checkUpdateObserverCalled(observer: SubscriptionObserver) {
            assertEquals(observer,observerCashed)
        }

        private var observerCashed : UiObserver<SubscriptionUiState> = object : SubscriptionObserver{
            override fun update(data: SubscriptionUiState) = Unit
        }
        override fun updateObserver(uiObserver: UiObserver<SubscriptionUiState>) {
            observerCashed = uiObserver
        }

        override fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save) {
            saveState.save(cashe)
        }

    }
}
private interface FakeHandleDeath : HandleDeath{
    fun checkFirstOpeningCalled(times :Int = 0)
    class Base : FakeHandleDeath {
        private var deathHappened = true
        private var firstOpeningCalledTimes = 0

        override fun firstOpening() {
            deathHappened = false
            firstOpeningCalledTimes ++
        }

        override fun checkFirstOpeningCalled(times :Int) {
            assertEquals(times,firstOpeningCalledTimes)
        }

        override fun didDeathHappen(): Boolean {
            return deathHappened
        }

        override fun deathHandled() {
            deathHappened = false
        }
    }
}
private class FakeMapper(private val observable: FakeObservable): SubscriptionResult.Mapper{
    override fun mapSuccess(canGoBackCallBack: (Boolean) -> Unit) {
        observable.update(SubscriptionUiState.Success)
        canGoBackCallBack.invoke(true)
    }

}