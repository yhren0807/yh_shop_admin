<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>用户列表
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
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="icon" label="用户头像">
                    <template slot-scope="scope">
                        <el-image
                                style="width: 50px; height: 50px"
                                :src="scope.row.icon"
                                :fit="fit"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="email" label="用户邮箱"></el-table-column>
                <el-table-column prop="nickName" label="用户昵称"></el-table-column>
                <el-table-column prop="note" label="备注信息"></el-table-column>
                <el-table-column prop="loginTime" label="最后登录时间"></el-table-column>
                <el-table-column prop="status" label="帐号启用状态">
                    <template slot-scope="scope">
                        <el-switch
                                v-model="scope.row.status"
                               >
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="handleRol(scope.row)"
                        >分配角色</el-button>
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
                <el-form-item label="用户名">
                    <el-input v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item label="真实名称">
                    <el-input v-model="form.nickName"></el-input>
                </el-form-item>
                <el-form-item label="头像">
                <FileUpload v-model="form.icon"></FileUpload>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="form.email"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input
                            type="textarea"
                            autosize
                            placeholder="请输入内容"
                            v-model="form.note">
                    </el-input>
                </el-form-item>
                <el-form-item label="账号启用状态" >
                    <el-radio-group v-model="form.status">
                    <el-radio :label="1">启用</el-radio>
                    <el-radio  :label="0">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="分配角色" :visible.sync="roleAdminVisible" width="20%">
            <el-select v-model="valueRole" multiple placeholder="请选择">
                <el-option
                        v-for="item in roleOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
            <span slot="footer" class="dialog-footer">
                <el-button @click="roleAdminVisible=false">取 消</el-button>
                <el-button type="primary" @click="saveUmsAdminRole">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

    import {queryUmsAdmin,queryUmsAdminById,saveUmsAdmin,updateUmsAdmin,deleteUmsAdmin} from "../../../api/ums/umsAdmin";
    import {queryByAdminIdUmsRole,queryUmsRole,saveAdminUmsRole} from "../../../api/ums/umsRole";
    import FileUpload from '../newfileUtils/FileUpload'
    export default {
        name: "tableUmsAdmin",
        components:{
            queryUmsAdmin,
            queryUmsAdminById,
            saveUmsAdmin,
            updateUmsAdmin,
            deleteUmsAdmin,
            queryByAdminIdUmsRole,
            queryUmsRole,
            saveAdminUmsRole,
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
                roleOptions:[],
                editVisible: false,
                roleAdminVisible:false,
                valueRole:[],
                selectAdminId:null,
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
                queryUmsAdmin(this.query).then(res => {
                    this.tableData = res.data.data.records;
                });
            },
            handleDelete(row){
                deleteUmsAdmin(row.id).then(res=>{
                    alert(res.data.msg);
                    this.getData();
                })
            },
            //编辑分配角色操作
            handleRol(row){
                this.selectAdminId=row.id
                this.roleOptions=[];
                this.valueRole=[];
                this.roleAdminVisible=true
                this.queryUmsRole(row.id)
                this.getValueRole(row.id)
            },
            //保存校色
            saveUmsAdminRole(){
                let list=[];
                for (let i = 0; i < this.valueRole.length ; i++) {
                    let valueRole=this.valueRole[i];
                    list.push({adminId:this.selectAdminId,roleId:valueRole})
                }
                saveAdminUmsRole(list).then(res=>{
                    alert(res.data.msg)
                })
                this.roleAdminVisible=false

            },
            //回显角色信息
            getValueRole(id){
                queryByAdminIdUmsRole(id).then(res=>{
                    this.valueRole=res.data.data;
                })
            },
            //查询所有的角色信息
            queryUmsRole(){
                let query={
                    current: 1,
                    size: 100
                };
                queryUmsRole(query).then(res => {
                    let list = res.data.data.records;
                    for (let i = 0; i <list.length ; i++) {
                       this.roleOptions.push({value:list[i].id,label:list[i].name})
                    }
                });
            },
            // 编辑操作
            handleEdit(index, row) {
                this.idx = index;
                if (row!=null && row!==undefined ){
                    this.queryUmsAdminById(row.id);
                }
                this.editVisible = true;

            },
            queryUmsAdminById(id){
                queryUmsAdminById(id).then(res=>{
                    this.form=res.data.data;
                })
            },
            // 保存编辑
            saveEdit() {
                if (this.form.id!=null && this.form.id!==undefined ){
                    this.updateUmsAdmin();
                }else {
                    this.saveUmsAdmin();
                }
            },
            saveUmsAdmin(){
                saveUmsAdmin(this.form).then(res=>{
                    alert(res.data.msg);
                    this.editVisible = false;
                    this.getData();
                    this.form=[];
                })
            },
            updateUmsAdmin(){
                updateUmsAdmin(this.form).then(res=>{
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