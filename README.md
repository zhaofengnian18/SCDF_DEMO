# SCDF_DEMO

## 目录结构
- ml-task *存放所有dataflow任务*
  - ml-transform *数据转换子任务*
  - ml-aggregate *聚合子任务*  
- ml-dispatcher *调度服务*

## 运行
```bash
cd ml-dispatcher
./gradlew build
cd ..
docker-compose up
```
查看  
[Dataflow](http://localhost:9393)  
[ML_DISPATCHER](http://localhost:9898/swagger-ui/index.html)  

顺序调用dispatcher接口  
register 注册app  
可在[Dataflow App List](http://localhost:9393/dashboard/#/apps)看到注册的app  
create 创建task  
可在[Dataflow Task List](http://localhost:9393/dashboard/#/tasks-jobs/tasks)看到创建的task   
launch 执行task  
可在[Dataflow Executions](http://localhost:9393/dashboard/#/tasks-jobs/task-executions)看到执行的executions