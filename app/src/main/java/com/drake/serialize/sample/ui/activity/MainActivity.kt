/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.serialize.sample.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.drake.serialize.sample.R
import com.drake.serialize.sample.model.MainStateViewModel
import com.drake.serialize.sample.model.ModelSerializable
import com.drake.serialize.serialize.serial
import com.drake.serialize.serialize.serialLazy
import com.drake.serialize.stateModels
import com.drake.statusbar.immersive


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var name: String by serial()
    private var model: ModelSerializable by serialLazy()
    private var simple: String by serial("默认值", "自定义键名")
    private val stateModel: MainStateViewModel by stateModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setSupportActionBar(toolbar)
        immersive(darkMode = true)


        // toolbar.setupWithNavController(
        //     nav.findNavController(),
        //     AppBarConfiguration(nav_view.menu, drawer)
        // )
        // val actionBarDrawerToggle =
        //     ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name)
        // drawer.addDrawerListener(actionBarDrawerToggle)
        // actionBarDrawerToggle.syncState()
        // nav_view.setupWithNavController(nav.findNavController())
    }
}
