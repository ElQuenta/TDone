package com.example.tdone.dataclasses

data class group(
    val name:String,
    val task:List<task>
){
    companion object{
        private var instance:group? = null

        fun getGroup(name:String, task:List<task>): group {
            if(instance == null){
                instance = group(name,task)
            }
            return instance!!
        }
    }
}

