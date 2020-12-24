<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>角色列表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-add" @click="handleEdit">新增</el-button>
            </div>
            <el-table
                    :data="tableData"
                    border
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
                    @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="权限名称"></el-table-column>
                <el-table-column prop="value" label="权限值"></el-table-column>
                <el-table-column prop="uri" label="权限值"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
                <el-table-column prop="icon" label="图标">
                    <template slot-scope="scope">
                        <el-image
                                style="width: 50px; height: 50px"
                                :src="scope.row.icon"
                                :fit="fit"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="type" label="权限类型">
                    <template slot-scope="scope">
                        {{scope.row.type===1?"目录":scope.row.type===2?"菜单":"按钮"}}
                    </template>
                </el-table-column>
                <el-table-column prop="status" label="启用状态">
                    <template slot-scope="scope">
                        <el-switch
                                v-model="scope.row.status">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleDelete(scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination block" >
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
        </div>
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form :model="form" label-width="70px">
                <el-form-item label="权限名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="权限值">
                    <el-input
                            v-model="form.value">
                    </el-input>
                </el-form-item>
                <el-form-item label="图标">
                    <el-input v-model="form.icon"></el-input>
                </el-form-item>
                <el-form-item label="账号启用状态" v-model="form.type">
                    <el-radio  value="0">目录</el-radio>
                    <el-radio  value="1">菜单</el-radio>
                    <el-radio  value="2">按钮</el-radio>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="form.sort"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

   import {queryUmsPermission,queryUmsPermissionById,saveUmsPermission,updateUmsPermission,deleteUmsPermission} from "../../../api/ums/umsPermission";
   import FileUpload from '../newfileUtils/FileUpload'
    export default {
        name: "tableUmsPermission",
        components:{
   queryUmsPermission,queryUmsPermissionById,saveUmsPermission,updateUmsPermission,deleteUmsPermission,
            FileUpload
        },
        data() {
            return {
                query: {
                    name: '',
                    current: 1,
                    size: 5
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                total: 0,
                form: {},
                idx: -1,
                id: -1
            }
        },
        created() {
            this.getData();
        },
        methods: {
            // 获取 easy-mock 的模拟数据
            getData() {
                queryUmsPermission(this.query).then(res => {
                    this.tableData = res.data.data.records;
                    this.total = res.data.data.total || 50;
                });
            },
            handleDelete(row){
                deleteUmsPermission(row.id).then(res=>{
                    alert(res.data.msg);
                    this.getData();
                })
            },
            // 编辑操作
            handleEdit(index, row) {
                this.idx = index;
                if (row!=null){
                    this.queryUmsPermissionById(row.id);
                }
                this.editVisible = true;
            },
            queryUmsPermissionById(id){
                queryUmsPermissionById(id).then(res=>{
                    this.form=res.data.data;
                })
            },
            // 保存编辑
            saveEdit() {
                if (this.form.id!=null && this.form.id!==undefined ){
                    this.updateUmsPermission();
                }else {
                    this.saveUmsPermission();
                }
            },
            saveUmsPermission(){
                saveUmsPermission(this.form).then(res=>{
                    alert(res.data.msg);
                    this.editVisible = false;
                    this.getData();
                    this.form=[];
                })
            },
            updateUmsPermission(){
                updateUmsPermission(this.form).then(res=>{
                    this.getData();
                    this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                    this.editVisible = false;
                })


            },
            handlePageChange(val){
                this.$set(this.query,"current",val)
                this.getData();
            }

        },
        mounted() {
        }
    }
</script>

<style>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .table {
        width: 100%;
        font-size: 14px;
    }
    .red {
        color: #ff0000;
    }
    .mr10 {
        margin-right: 10px;
    }
    .table-td-thumb {
        display: block;
        margin: auto;
        width: 40px;
        height: 40px;
    }
</style>