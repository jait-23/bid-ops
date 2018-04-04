(function() {
    'use strict';
    angular
        .module('bidopscoreApp')
        .factory('Solicitations', Solicitations);

    Solicitations.$inject = ['$resource', 'DateUtils'];

    function Solicitations ($resource, DateUtils) {
        var resourceUrl =  'api/solicitations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                	data = angular.fromJson(data);    
                    if (angular.isUndefined(data.requiredDocuments) 
                    		|| data.requiredDocuments === null 
                    		|| data.requiredDocuments ==="") {
                    	data.requiredDocuments = {};
                    	data.requiredDocuments = {"type" : "", "platform" : ""};
                    	data.requiredDocuments["Manuals"] = {"Name" : "", "Type": "", "URL" : ""};
                    	
                    }
                    
                    else {
                    	data.requiredDocuments = JSON.parse(data.requiredDocuments)
                    	
                    var extraInfo  = data.requiredDocuments;
                    	  if (angular.isUndefined(extraInfo.type) 
                          		|| extraInfo.type === null 
                          		|| extraInfo.type ==="") {
                    		  data.requiredDocuments["type"]= "";
                    	  }  
                    	  else if(angular.isUndefined(extraInfo.platform) 
                                		|| extraInfo.platform === null 
                                		|| extraInfo.platform ==="") {
                          		  data.requiredDocuments["platform"]= "";
                          	  }
                    	  else if(angular.isUndefined(extraInfo.Manuals) 
                          		|| extraInfo.Manuals === null 
                          		|| extraInfo.Manuals ==="") {
                    		  	data.requiredDocuments["Manuals"] = {"Name" : "", "Type": "", "URL" : ""};
                    	  }
                    }
                    
                    return data;
                }
            },
            'update': { 
            	url : 'api/solicitations/:id',
            	method:'PUT'
            },
            'edit': {
            	url : 'api/solicitations/:id',
            	method:'PUT'
            },
            'delete': {
            	method : 'DELETE',
            	url : 'api/solicitations/:id'
            },
            'save': {
            	method : 'POST',
            	url : 'api/solicitations/:id'
            }
        });
    }
})();