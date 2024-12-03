var call = require("../../utils/request.js")
var help = require("../../utils/help.js")

Page({

    data: {
        list:[],
    },
    onLoad: function (options) {
        const { id } = options;
    
        this.getKnowledgeList(id);
     },

      getKnowledgeList(id){
        call.getData('wx/getMagazineListByClassifiedId/'+id , this.onSuccessKnowledgeAll, this.onFaiKnowledgeAll);
      },
     onSuccessKnowledgeAll(res) {
      if(res.code == 20000){
        this.setData({
            list:res.data.list
      })
    } 
    },
  
    onFaiKnowledgeAll() {
      help.show("网络请求失败");
    }
})