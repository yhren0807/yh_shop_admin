<template>
    <el-card class="form-container" shadow="never">
        <div v-for="(cate,index) in allResourceCate" :class="index===0?'top-line':null" :key="'cate'+cate.id">
            <el-row class="table-layout" style="background: #F2F6FC;">
                <el-checkbox v-model="cate.checked"
                             :indeterminate="isIndeterminate(cate.id)"
                             @change="handleCheckAllChange(cate)">
                    {{cate.name}}
                </el-checkbox>
            </el-row>
            <el-row class="table-layout">
                <el-col :span="8" v-for="resource in getResourceByCate(cate.id)" :key="resource.id" style="padding: 4px 0">
                    <el-checkbox v-model="resource.checked" @change="handleCheckChange(resource)">
                        {{resource.name}}
                    </el-checkbox>
                </el-col>
            </el-row>
        </div>
        <div style="margin-top: 20px" align="center">
            <el-button type="primary" @click="handleSave()">保存</el-button>
            <el-button @click="handleClear()">清空</el-button>
        </div>

    </el-card>
</template>

<script>
    import {queryResourceByRoleId,queryUmsResources,queryUmsRoleResourceByRoleId,saveUmsRoleResourceByRoleId} from "../../../api/ums/resource";
    import {queryResourceCate} from "../../../api/ums/umsResourceCate";

    export default {
        name: "checkResouece",
        components:{
        queryResourceCate,queryResourceByRoleId,queryUmsResources,queryUmsRoleResourceByRoleId,saveUmsRoleResourceByRoleId
        },
        data() {
            return {
                allResourceCate:[],
                roleId:this.$route.query.id,
                allResource: [],
                resourceList:[],
            }
        },

        methods: {
            getData(){
                queryResourceCate().then(res=>{
                    this.allResourceCate=res.data.data
                    for (let i = 0; i < this.allResourceCate.length; i++) {
                        this.allResourceCate[i].checked = false;
                    }
                })
                this.queryResourceListAll();
            },
            queryByRoleIdResurce(){
                queryUmsRoleResourceByRoleId(this.roleId).then(res=>{
                    this.getResurceChecked(res.data.data)
                })
            },
            getResurceChecked(list){
                for (let i = 0; i <this.allResource.length ; i++) {
                    for (let j = 0; j <list.length ; j++) {
                        if (this.allResource[i].id===list[j].resourceId){
                            this.allResource[i].checked=true;
                        }
                    }
                    this.handleCheckChange(this.allResource[i]);
                }
                this.$forceUpdate();
            },
            getResourceByCate(id){
                let list=[];
                for (let i = 0; i <this.allResource.length ; i++) {
                    var allResourceElement = this.allResource[i];
                    if (allResourceElement.categoryId===id){
                        list.push(allResourceElement);
                    }
                }
                return list;

            },
            queryResourceListAll(){
                let query={
                    current:1,
                    size: 50
                }
                queryUmsResources(query).then(res=>{
                  this.allResource=res.data.data.records
                    for (let i = 0; i < this.allResource.length; i++) {
                        this.allResource[i].checked = false;
                    }
                    this.queryByRoleIdResurce();
                })

            },
            isAllChecked(categoryId) {
                let cateResources = this.getResourceByCate(categoryId);
                if (cateResources == null) return false;
                let checkedCount = 0;
                for (let i = 0; i < cateResources.length; i++) {
                    if (cateResources[i].checked === true) {
                        checkedCount++;
                    }
                }
                if(checkedCount===0){
                    return false;
                }
                return checkedCount === cateResources.length;
            },
            isIndeterminate(categoryId) {
                let cateResources = this.getResourceByCate(categoryId);
                if (cateResources == null) return false;
                let checkedCount = 0;
                for (let i = 0; i < cateResources.length; i++) {
                    if (cateResources[i].checked === true) {
                        checkedCount++;
                    }
                }
                return !(checkedCount === 0 || checkedCount === cateResources.length);
            },
            handleClear() {
                this.allResourceCate.forEach(item => {
                    item.checked = false;
                });
                this.allResource.forEach(item => {
                    item.checked = false;
                });
                this.$forceUpdate();
            },
            //模块全选根据模分组id查询这一组中的数据并将这一组的选中状态变为true
            handleCheckAllChange(cate) {
                let cateResources = this.getResourceByCate(cate.id);
                for (let i = 0; i < cateResources.length; i++) {
                    cateResources[i].checked = cate.checked;
                }
                this.$forceUpdate();
            },
            //选一个选模块 根据你选的模块扎到你所选的模块id并将这个模块的选中状态赋为true
            handleCheckChange(resource) {
                this.allResourceCate.forEach(item=>{
                    if(item.id===resource.categoryId){
                            item.checked = this.isAllChecked(resource.categoryId);
                    }
                });
                this.$forceUpdate();
            },
            handleSave(){
                this.$confirm('是否分配资源？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    if (this.allResourceCate !== [] && this.allResource !== []) {
                        let list = [];
                        this.allResource.forEach(res => {
                            if (res.checked === true) {
                                list.push({roleId: this.roleId, resourceId: res.id})
                            }
                        })
                        if (list !== []) {
                            saveUmsRoleResourceByRoleId(list).then(res => {
                                alert(res.data.msg);
                            })
                        }
                    }
                })
            }

        },
        mounted() {
            this.getData();

        }
    }
</script>

<style scoped>
    .table-layout {
        padding: 20px;
        border-left: 1px solid #DCDFE6;
        border-right: 1px solid #DCDFE6;
        border-bottom: 1px solid #DCDFE6;
    }

    .top-line {
        border-top: 1px solid #DCDFE6;
    }
</style>