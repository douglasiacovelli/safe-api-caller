package br.com.myapplication

import android.util.Log
import com.google.common.reflect.TypeToken
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class CoroutineCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        Log.d("dbg", "returnType: $returnType. rawType: ${getRawType(returnType)}")
        if (getRawType(returnType) == Call::class.java) {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            Log.d("dbg", "callType: $callType}")
            Log.d("dbg", "callType: ${getRawType(callType)}")
            val innerResponseType = getParameterUpperBound(0, callType as ParameterizedType)
            val callResponseType = object : TypeToken<Call<Any>>() {}.type
            if (getRawType(callType) == ResultWrapper::class.java) {
                val delegate: CallAdapter<Any, *> =
                    retrofit.nextCallAdapter(this, callResponseType, annotations) as CallAdapter<Any, *>

                return object: CallAdapter<Any, ResultWrapper<*>> {
                    override fun adapt(call: Call<Any>): ResultWrapper<*> {
                        Log.d("dbg", "adapt call")
                        val result = delegate.adapt(call)

                        Log.d("dbg", "result: ${result}")
                        return ResultWrapper.Success(result)
                    }

                    override fun responseType(): Type {
                        return delegate.responseType()
                    }
                }
            }
        }
        return null
    }

//    private class BodyCallAdapter<T>(
//        private val delegate: CallAdapter<Any, ResultWrapper<*>>
//    ) : CallAdapter<Any, ResultWrapper<T>> {
//
//        override fun responseType() = ResultWrapper::class.java
//
//        override fun adapt(call: Call<Any>): ResultWrapper<T> {
//            val callResult = delegate.adapt(call)
//            return ResultWrapper(callResult.)
//        }
//    }
}