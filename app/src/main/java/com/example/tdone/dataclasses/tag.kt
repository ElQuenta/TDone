package com.example.tdone.dataclasses

data class tag(
    val name:String,
){
    companion object{
        private var instance:tag? = null

        fun getIntance(name:String): tag {
            if(instance == null){
                instance = tag(name)
            }
            return instance!!
        }
    }
}
