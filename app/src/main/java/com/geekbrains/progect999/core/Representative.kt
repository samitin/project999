package com.geekbrains.progect999.core

import com.geekbrains.progect999.main.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface Representative<T : Any> {
    /**
     * начать получать обновления
     */
    fun startGettingUpdates(callBack: UiObserver<T>) = Unit
    /**
     * прекратить получать обновления
     */
    fun stopGettingUpdates() = Unit

    abstract class Abstract<T : Any>(
        private val runAsync: RunAsync,
    ) : Representative<T>{
        private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        protected fun <T : Any> handleAsync(
            backgroundBlock: suspend () -> T,
            uiBlock : (T) -> Unit
        ){
            runAsync.runAsync(coroutineScope,backgroundBlock,uiBlock)
        }
        protected suspend fun <T:Any> handleAsyncInternal(
            backgroundBlock: suspend () -> T,
            uiBlock : (T) -> Unit
        ){
            runAsync.runAsync(backgroundBlock,uiBlock)
        }
        protected fun clear(){
            runAsync.clear()
        }
    }
}
interface RunAsync{
    fun <T:Any>runAsync(
        scope: CoroutineScope,
        backgroundBlock: suspend () -> T,
        uiBlock : (T) -> Unit
    )
    suspend fun <T:Any>runAsync(
        backgroundBlock: suspend () -> T,
        uiBlock : (T) -> Unit
    )
    fun clear()
    class Base(
        private val dispatchersList: DispatchersList
    ) : RunAsync {
        private var job : Job?= null
        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            backgroundBlock: suspend () -> T,
            uiBlock: (T) -> Unit
        ) {
            job = scope.launch(dispatchersList.background()) {
                val result = backgroundBlock.invoke()
                withContext(dispatchersList.ui()){
                    uiBlock.invoke(result)
                }
            }
        }

        override suspend fun <T : Any> runAsync(backgroundBlock: suspend () -> T, uiBlock: (T) -> Unit) {
            withContext(dispatchersList.background()) {
                val result = backgroundBlock.invoke()
                withContext(dispatchersList.ui()){
                    uiBlock.invoke(result)
                }
            }
        }

        override fun clear() {
            job?.cancel()
            job = null
        }

    }
}