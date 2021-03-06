package cn.authing.core.mgmt

import cn.authing.core.graphql.GraphQLCall
import cn.authing.core.graphql.GraphQLResponse
import cn.authing.core.http.HttpCall
import cn.authing.core.types.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 管理组织机构
 */
class OrgManagementClient(private val client: ManagementClient) {
    /**
     * 创建分组
     */
    fun create(param: CreateOrgParam): GraphQLCall<CreateOrgResponse, Org> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<CreateOrgResponse>>() {}) {
            it.result
        }
    }

    /**
     * 删除分组
     */
    fun deleteById(id: String): GraphQLCall<DeleteOrgResponse, CommonMessage> {
        val param = DeleteOrgParam(id)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<DeleteOrgResponse>>() {}) {
            it.result
        }
    }

    /**
     * 获取分组列表
     */
    fun list(param: OrgsParam): GraphQLCall<OrgsResponse, PaginatedOrgs> {
        if (param.limit == null) param.limit = -1
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<OrgsResponse>>() {}) {
            it.result
        }
    }

    /**
     * 根据节点Id查询节点
     */
    fun findNodeById(nodeId: String): GraphQLCall<NodeByIdResponse, Node> {
        val param = NodeByIdParam(id = nodeId)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<NodeByIdResponse>>() {}) {
            it.result
        }
    }

    /**
     * 创建节点
     */
    fun addNode(param: AddNodeV2Param): GraphQLCall<AddNodeV2Response, Node> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<AddNodeV2Response>>() {}) {
            it.result
        }
    }

    /**
     * 修改节点
     */
    fun updateNode(param: UpdateNodeParam): GraphQLCall<UpdateNodeResponse, Node> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<UpdateNodeResponse>>() {}) {
            it.result
        }
    }

    /**
     * 删除节点
     */
    fun deleteNode(param: DeleteNodeParam): GraphQLCall<DeleteNodeResponse, CommonMessage> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<DeleteNodeResponse>>() {}) {
            it.result
        }
    }

    /**
     * 移动节点
     */
    fun moveNode(orgId: String, nodeId: String, targetParentId: String): GraphQLCall<MoveNodeResponse, Org> {
        val param = MoveNodeParam(orgId, nodeId, targetParentId);
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<MoveNodeResponse>>() {}) {
            it.result
        }
    }

    /**
     * 判断是否为根节点
     */
    fun isRootNode(nodeId: String, orgId: String): GraphQLCall<IsRootNodeResponse, Boolean> {
        val param = IsRootNodeParam(nodeId = nodeId, orgId = orgId)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<IsRootNodeResponse>>() {}) {
            it.result
        }
    }

    /**
     * 获取一个组织的根节点
     */
    fun rootNode(param: RootNodeParam): GraphQLCall<RootNodeResponse, Node> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<RootNodeResponse>>() {}) {
            it.result
        }
    }

    /**
     * 获取子节点列表
     */
    fun listChildren(orgId: String, nodeId: String): GraphQLCall<ChildrenNodesResponse, List<Node>> {
        val param = ChildrenNodesParam(orgId = orgId, nodeId = nodeId)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<ChildrenNodesResponse>>() {}) {
            it.result
        }
    }

    /**
     * 模糊搜索组织节点
     */
    fun searchNodes(param: SearchNodesParam): GraphQLCall<SearchNodesResponse, List<Node>> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<SearchNodesResponse>>() {}) {
            it.result
        }
    }

    /**
     * 节点添加成员
     */
    fun addMembers(nodeId: String, userIds: List<String>): GraphQLCall<AddMemberResponse, Node> {
        val param = AddMemberParam(nodeId = nodeId, userIds = userIds)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<AddMemberResponse>>() {}) {
            it.result
        }
    }

    /**
     * 获取节点成员
     */
    fun listMembers(param: NodeByIdWithMembersParam): GraphQLCall<NodeByIdWithMembersResponse, Node> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<NodeByIdWithMembersResponse>>() {}) {
            it.result
        }
    }

    /**
     * 节点删除成员
     */
    fun removeMembers(nodeId: String, userIds: List<String>): GraphQLCall<RemoveMemberResponse, Node> {
        val param = RemoveMemberParam(nodeId = nodeId, userIds = userIds)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<RemoveMemberResponse>>() {}) {
            it.result
        }
    }

    /**
     * 分组详情
     */
    fun findById(id: String): GraphQLCall<OrgResponse, Org> {
        val param = OrgParam(id)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<OrgResponse>>() {}) {
            it.result
        }
    }

    /**
     * 通过一个 JSON 树结构导入组织机构
     */
    fun importByJson(json: String): HttpCall<RestfulResponse<OrgNode>, OrgNode> {
        val gson = Gson()
        val node = gson.fromJson(json, Map::class.java)
        val data = mapOf("filetype" to "json", "file" to node)

        val dataStr = gson.toJson(data)
        return client.createHttpPostCall(
            "${client.host}/api/v2/orgs/import",
            dataStr,
            object : TypeToken<RestfulResponse<OrgNode>>() {}) {
            it.data
        }
    }

    /**
     * 导出所有组织机构数据
     */
    fun exportAll(): HttpCall<RestfulResponse<List<OrgNode>>, List<OrgNode>> {
        return client.createHttpGetCall(
            "${client.host}/api/v2/orgs/export",
            object : TypeToken<RestfulResponse<List<OrgNode>>>() {}) {
            it.data
        }
    }

    /**
     * 导入某个组织机构数据
     */
    fun exportByOrgId(orgId: String): HttpCall<RestfulResponse<OrgNode>, OrgNode> {
        return client.createHttpGetCall(
            "${client.host}/api/v2/orgs/export?org_id=${orgId}",
            object : TypeToken<RestfulResponse<OrgNode>>() {}) {
            it.data
        }
    }

    fun listAuthorizedResourcesByNodeId(
        param: ListNodeByIdAuthorizedResourcesParam
    ): GraphQLCall<ListNodeByCodeAuthorizedResourcesResponse, Node> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<ListNodeByCodeAuthorizedResourcesResponse>>() {}
        ) { it.result }
    }
}