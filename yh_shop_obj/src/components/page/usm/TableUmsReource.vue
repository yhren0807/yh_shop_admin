<template>
    <div class="app-container">


        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>
            <el-button size="mini" class="btn-add" @click="handleResourceCate()" style="margin-left: 20px">资源分类</el-button>
            <el-button size="mini" class="btn-add" @click="handleAdd()" style="margin-left: 20px">添加</el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="resourceTable"
                      :data="list"
                      style="width: 100%;"
                      v-loading="listLoading" border>
                <el-table-column label="编号" width="100" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="资源名称" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>
                <el-table-column label="资源路径" align="center">
                    <template slot-scope="scope">{{scope.row.url}}</template>
                </el-table-column>
                <el-table-column label="描述" align="center">
                    <template slot-scope="scope">{{scope.row.description}}</template>
                </el-table-column>
                <el-table-column label="添加时间" width="160" align="center">
                    <template slot-scope="scope">{{scope.row.createTime | formatDateTime}}</template>
                </el-table-column>
                <el-table-column label="操作" width="140" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini"
                                   type="text"
                                   @click="handleUpdate(scope.$index, scope.row)">
                            编辑
                        </el-button>
                        <el-button size="mini"
                                   type="text"
                                   @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="pagination-container">
            <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :page-sizes="[3, 4, 5, 6]"
                    :current-page="query.current"
                    :page-size="query.size"
                    :total="total"
                    @current-change="handlePageChange"
            ></el-pagination>
        </div>
        <el-dialog
                :title="isEdit?'编辑资源':'添加资源'"
                :visible.sync="dialogVisible"
                width="40%">
            <el-form :model="resource"
                     ref="resourceForm"
                     label-width="150px" size="small">
                <el-form-item label="资源名称：">
                    <el-input v-model="resource.name" style="width: 250px"></el-input>
                </el-form-item>
                <el-form-item label="资源路径：">
                    <el-input v-model="resource.url" style="width: 250px"></el-input>
                </el-form-item>
                <el-form-item label="资源分类：">
                    <el-select v-model="resource.categoryId" placeholder="全部" clearable style="width: 250px">
                        <el-option v-for="item in categoryOptions"
                                   :key="item.value"
                                   :label="item.label"
                                   :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input v-model="resource.description"
                              type="textarea"
                              :rows="5"
                              style="width: 250px"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleDialogConfirm()" size="small">确 定</el-button>
      </span>
        </el-dialog>
    </div>
</template>

<script>
   import {queryUmsResources,deleteUmsResource,saveUmsResource,updateUmsResource,queryUmsResourceById} from "../../../api/ums/resource";

   import {queryResourceCate} from "../../../api/ums/umsResourceCate";

   const defaultResource = {
       id: null,
       name: null,
       url: null,
       categoryId: null,
       description:''
   };
   export default {
        name: "TableUmsReource",
       components:{
           queryUmsResources,queryResourceCate,deleteUmsResource,saveUmsResource,updateUmsResource,queryUmsResourceById
       },
        data() {
            return {
                query: {
                    name: '',
                    current: 1,
                    size: 5
                },
                list: null,
                total: null,
                listLoading: false,
                dialogVisible: false,
                resource: Object.assign({}, defaultResource),
                isEdit: false,
                categoryOptions:[],
                defaultCategoryId:null
            }
        },
        methods: {
            getList(){
                queryUmsResources(this.query).then(res=>{
                    this.list = res.data.data.records;
                    this.total = res.data.data.total || 50;
                })
            },
            handlePageChange(val){
                this.$set(this.query,"current",val);
                queryUmsResources(this.query).then(res=>{
                    this.list = res.data.data.records;
                    this.total = res.data.data.total || 50;
                })
            },
            //新增弹框
            handleAdd(){
                this.isEdit=false
                this.dialogVisible=true
                this.resource=Object.assign({}, defaultResource);
                this.categoryOptions=[];
                this.queryResourceCate();
            },
            handleUpdate(index,row){
                this.isEdit=true
                this.dialogVisible=true
                this.categoryOptions=[];
                this.resource=Object.assign({}, defaultResource);
                this.queryResourceCate();
                this.queryUmsResourceById(row.id);
            },
            //回显
            queryUmsResourceById(id){
                queryUmsResourceById(id).then(res=>{
                    this.resource=res.data.data;
                })
            },
            //资源分类
            queryResourceCate(){
                queryResourceCate().then(res=>{
                    var data1 = res.data.data;
                    for (let i = 0; i <data1.length ; i++) {
                        this.categoryOptions.push({value:data1[i].id,label:data1[i].name})
                    }
                })
            },
            //新增
            handleDialogConfirm(){
                if (this.resource.id!=null &&this.resource.id!=undefined ){
                    updateUmsResource(this.resource).then(res=>{
                        alert(res.data.msg);
                        this.dialogVisible=false;
                        this.getList();
                    })
                }else {
                    saveUmsResource(this.resource).then(res=>{
                        alert(res.data.msg);
                        this.dialogVisible=false;
                        this.getList();
                    })
                }

            },
            handleDelete(index,row){
                deleteUmsResource(row.id).then(res=>{
                    alert(res.data.msg);
                    this.dialogVisible=false;
                    this.getList();
                })
            },
            handleResourceCate(){
                this.$router.push("/resourceCate");
            },

        },
        mounted() {
            this.getList()
        }
    }
</script>

<style scoped>

</style>