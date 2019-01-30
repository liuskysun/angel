/*
 * Tencent is pleased to support the open source community by making Angel available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/Apache-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */


package com.tencent.angel.ml.classification

import com.tencent.angel.ml.core.conf.MLCoreConf
import com.tencent.angel.ml.core.graphsubmit.GraphModel
import com.tencent.angel.ml.core.network.Identity
import com.tencent.angel.ml.core.network.layers.LossLayer
import com.tencent.angel.ml.core.network.layers.verge.SimpleInputLayer
import com.tencent.angel.ml.core.optimizer.Optimizer
import com.tencent.angel.ml.core.optimizer.loss.HingeLoss
import com.tencent.angel.worker.task.TaskContext
import org.apache.hadoop.conf.Configuration

/**
  * SVM model
  *
  */


class SupportVectorMachine(conf: Configuration, _ctx: TaskContext = null) extends GraphModel(conf, _ctx) {
  val ipOptName: String = sharedConf.get(MLCoreConf.ML_INPUTLAYER_OPTIMIZER, MLCoreConf.DEFAULT_ML_INPUTLAYER_OPTIMIZER)
  val optimizer: Optimizer = OptUtils.getOptimizer(ipOptName)

  override def buildNetwork(): Unit = {
    val input = new SimpleInputLayer("input", 1, new Identity(), optimizer)
    new LossLayer("simpleLossLayer", input, new HingeLoss())
  }
}