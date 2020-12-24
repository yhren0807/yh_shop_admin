<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>菜单列表
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
                <el-table-column prop="title" label="菜单名称"></el-table-column>
                <el-table-column prop="level" label="菜单级数">
                <template slot-scope="scope">
                    {{scope.row.level===0?"一级":"二级"}}
                </template>
            </el-table-column>
                <el-table-column prop="name" label="前端名称"></el-table-column>
                <el-table-column prop="icon" label="图标">
                    <template slot-scope="scope">
                        <i style="width: 40px" :class="scope.row.icon"></i>
                    </template>
                </el-table-column>
                <el-table-column prop="sort" label="菜单排序"></el-table-column>
                <el-table-column prop="hidden" label="是否显示">
                    <template slot-scope="scope">
                        <el-switch
                                v-model="scope.row.hidden"
                                active-value="1"
                                inactive-value="0"
                             >
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="设置" width="120" align="center">
                    <template slot-scope="scope">
                        <el-button
                                v-if="scope.row.level===0"
                                size="mini"
                                type="text"
                                @click="handleShowNextLevel(scope.$index, scope.row)">查看下级
                        </el-button>
                        <el-button
                                v-else
                                size="mini"
                                type="text"
                                @click="handleShowPrveLevel(scope.$index, scope.row)">查看上级
                        </el-button>
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
            <el-form :model="menu"
                     :rules="rules"
                     label-width="150px">
                <el-form-item label="菜单名称：" prop="title">
                    <el-input v-model="menu.title"></el-input>
                </el-form-item>
                <el-form-item label="上级菜单:" prop="parentId">
                    <el-select v-model="menu.parentId"
                               placeholder="请选择菜单">
                        <el-option
                                :key="0"
                                label="无上级目录"
                                :value="0">
                        </el-option>
                        <el-option
                                v-for="item in selectMenuList"
                                :key="item.id"
                                :label="item.title"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="前端名称：" prop="name">
                    <el-input v-model="menu.name"></el-input>
                </el-form-item>
                <el-form-item label="前端图标：" prop="icon">
                    <el-input v-model="menu.icon" style="width: 80%"></el-input>
                    <svg-icon style="margin-left: 8px" :icon-class="menu.icon"></svg-icon>
                </el-form-item>
                <el-form-item label="是否显示：">
                    <el-radio-group v-model="menu.hidden">
                        <el-radio :label="0">是</el-radio>
                        <el-radio :label="1">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序：">
                    <el-input v-model="menu.sort"></el-input>
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
    import {queryUmsMenu,deleteUmsMenu,saveUmsMenu,updateUmsMenu,queryUmsMenuById} from "../../../api/ums/umsMenu";
    import FileUpload from '../newfileUtils/FileUpload'
    export default {
        name: "tableUmsRole",
        components:{
            updateUmsMenu,
            saveUmsMenu,
            deleteUmsMenu,
            queryUmsMenu,
            queryUmsMenuById,
            FileUpload
        },
        data() {
            return {
                query: {
                    name: '',
                    level:0,
                    current: 1,
                    size: 5
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                total: 0,
                selectMenuList:[],
                menu: {},
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
                queryUmsMenu(this.query).then(res => {
                    this.tableData = res.data.data.records;
                    this.total = res.data.data.total || 50;
                });
            },
            handleDelete(row){
                deleteUmsMenu(row.id).then(res=>{
                    alert(res.data.msg);
                    this.$set(this.query,"current",1);
                    this.getData();
                })
            },
            // 编辑操作
            handleEdit(index,row) {
                this. menu={};
                this.selectMenuList=[];
                this.editVisible = true;
                this.getSelectMenuList()
                if (row.id!=null){
                    this.queryUmsMenuById(row.id);
                }
            },
            queryUmsMenuById(id){
                queryUmsMenuById(id).then(res=>{
                    this.menu=res.data.data;
                })
            },
            handleShowNextLevel(index,row){
                this.$set(this.query,"level",1);
                this.$set(this.query,"pid",row.id);
                queryUmsMenu(this.query).then(res => {
                    this.tableData = res.data.data.records;
                    this.total = res.data.data.total || 50;
                });
            },
            handleShowPrveLevel(){
                this.$set(this.query,"level",0);
                this.$set(this.query,"pid",null);
                    queryUmsMenu(this.query).then(res => {
                  this.tableData = res.data.data.records;
                    this.total = res.data.data.total || 50;
                });
            },
            getSelectMenuList(){
                   let query={
                       level:0,
                       current: 1,
                       size: 20
                   }
                queryUmsMenu(query).then(res => {
                    let list = res.data.data.records;
                    for (let i = 0; i <list.length; i++) {
                        this.selectMenuList.push({title:list[i].title,id:list[i].id})
                    }
                });
            },
            // 保存编辑
            saveEdit() {
                if (this.menu.id!=null && this.menu.id!==undefined ){
                    this.updateUmsAdmin();
                }else {
                    this.saveUmsAdmin();
                }
            },
            saveUmsAdmin(){
                if ( this.menu.parentId!=null && this.menu.parentId!==0){
                 this.$set(this.menu,"level",1)
                }
                saveUmsMenu(this.menu).then(res=>{
                   alert(res.data.msg);
                    this.editVisible = false;
                    this.getData();
                })
            },
            updateUmsAdmin(){
                updateUmsMenu(this.menu).then(res=>{
                   alert(res.data.msg)
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