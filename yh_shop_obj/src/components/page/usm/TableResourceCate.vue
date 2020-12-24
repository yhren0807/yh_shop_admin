<template>
    <div class="app-container">
        <el-card shadow="never" class="operate-container">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>
            <el-button size="mini" class="btn-add" @click="handleAdd()">添加</el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="resourceCategoryTable"
                      :data="list"
                      style="width: 100%;"
                      v-loading="listLoading" border>
                <el-table-column label="编号" width="100" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="名称" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>
                <el-table-column label="创建时间" align="center">
                    <template slot-scope="scope">{{scope.row.createTime}}</template>
                </el-table-column>
                <el-table-column label="排序" align="center">
                    <template slot-scope="scope">{{scope.row.sort}}</template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini"
                                   type="text"
                                   @click="handleUpdate(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button size="mini"
                                   type="text"
                                   @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog
                title="添加分类"
                :visible.sync="dialogVisible"
                width="40%">
            <el-form :model="resourceCategory"
                     label-width="150px" size="small">
                <el-form-item label="名称：">
                    <el-input v-model="resourceCategory.name" style="width: 250px"></el-input>
                </el-form-item>
                <el-form-item label="排序：">
                    <el-input v-model="resourceCategory.sort" style="width: 250px"></el-input>
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
    import {queryResourceCate,deleteResourceCate,saveUmsResourceCate,updateUmsResourceCate,queryUmsResourceCateById} from "../../../api/ums/umsResourceCate";
    const defaultResourceCategory={
        name:null,
        sort:0
    };
    export default {
        name: "TableResourceCate",
        components:{
            queryResourceCate,deleteResourceCate,saveUmsResourceCate,updateUmsResourceCate,queryUmsResourceCateById
        },
        data() {
            return {
                list:[],
                listLoading: false,
                dialogVisible:false,
                isEdit:false,
                resourceCategory:Object.assign({},defaultResourceCategory)
            }
        },

        methods: {
            handleAdd() {
                this.dialogVisible = true;
                this.isEdit = false;
                this.resourceCategory = Object.assign({}, defaultResourceCategory);
            },
            handleUpdate(index, row) {
                this.dialogVisible = true;
                this.isEdit = true;
                this.resourceCategory = Object.assign({}, defaultResourceCategory);
                this.queryUmsResourceCateById(row.id)
            },
            queryUmsResourceCateById(id){
                queryUmsResourceCateById(id).then(res=>{
                    this.resourceCategory=res.data.data;
                })
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该分类?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteResourceCate(row.id).then(res => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getList();
                    });
                });
            },
            handleDialogConfirm() {
                this.$confirm('是否要确认?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    if (this.isEdit) {
                        updateUmsResourceCate(this.resourceCategory).then(response => {
                            this.$message({
                                message: '修改成功！',
                                type: 'success'
                            });
                            this.dialogVisible = false;
                            this.getList();
                        })
                    } else {
                        alert(JSON.stringify(this.resourceCategory));
                        saveUmsResourceCate(this.resourceCategory).then(response => {
                            this.$message({
                                message: '添加成功！',
                                type: 'success'
                            });
                            this.dialogVisible = false;
                            this.getList();
                        })
                    }
                })
            },
            getList() {
                this.listLoading = true;
                queryResourceCate().then(res=>{
                    this.listLoading = false;
                    this.list = res.data.data;
                })

            }
        },
        mounted() {
            this.getList()
        }
    }
</script>

<style scoped>

</style>