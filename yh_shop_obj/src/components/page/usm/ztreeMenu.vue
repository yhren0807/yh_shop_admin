<template>
    <div style="margin: 0 auto" align="center">
        <el-card class="form-container" shadow="never">
            <el-tree
                    :data="data"
                    show-checkbox
                    default-expand-all
                    node-key="id"
                    ref="tree"
                    highlight-current
                    :props="defaultProps">
            </el-tree>
            <div style="margin-top: 20px" align="center">
                <el-button type="primary" @click="handleSave()">保存</el-button>
                <el-button @click="handleClear()">清空</el-button>
            </div>
            <div class="buttons">
                <el-button @click="getCheckedKeys">通过 key 获取</el-button>
            </div>
        </el-card>

    </div>
</template>

<script>
    import {queryZtreeUmsMenu,queryUmsRoleIdMenu,saveUmsRoleIdMenu} from "../../../api/ums/umsMenu";

    export default {
        name: "ztreeMenu",
        components:{
            queryZtreeUmsMenu,
            saveUmsRoleIdMenu
        },
        data() {
            return {
                data:[],
                defaultProps: {
                    children: 'children',
                    label: 'label'
                },
                roleId:this.$route.query.id,
                menuListId:[],
            }
        },
        methods: {
            getData(){
                queryZtreeUmsMenu().then(res=>{
                    this.data=res.data.data
                })
            },
            getByRoleIdMenu(){
                this.menuListId=[];
                if (this.roleId!=null){
                    queryUmsRoleIdMenu(this.roleId).then(res=>{
                        let list=res.data.data
                        for (let i = 0; i <list.length ; i++) {
                            this.menuListId.push(list[i].menuId);
                        }
                    })
                    this.setCheckedKeys()
                }
            },
            handleSave(){
                var checkedKeys = this.$refs.tree.getCheckedKeys();
                var list=[];
                for (let i = 0; i <checkedKeys.length ; i++) {
                    list.push({roleId:this.roleId,menuId:checkedKeys[i]})
                }
                if (list!=null && list!==[]){
                    saveUmsRoleIdMenu(list).then(res=>{
                       alert(res.data.msg);
                    })
                }
            },
            setCheckedKeys(){
                this.$refs.tree.setCheckedKeys(this.menuListId);
            }
        },
        mounted() {
            this.getData();
            this.getByRoleIdMenu();
        }
    }
</script>

<style scoped>

</style>