(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SecondaryEvaluationController', SecondaryEvaluationController);

    SecondaryEvaluationController.$inject = ['$rootScope', '$scope', '$state', 'SecondaryEvaluation', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Bidders'];

    function SecondaryEvaluationController($rootScope, $scope, $state, SecondaryEvaluation, ParseLinks, AlertService, paginationConstants, pagingParams, Bidders) {

        var vm = this;
        console.log(vm);
        
        vm.secondaryEvaluation = SecondaryEvaluation.query();
        console.log(vm.secondaryEvaluation);
    	vm.bidders = Bidders.query();
    	$scope.secondaryEvaluationTotal = [];
		$scope.secondaryEvaluation = [];
		console.log(vm.secondaryEvaluations);
    	SecondaryEvaluation.query().$promise
		.then(function(result) {
			console.log("promised SecondaryEvaluation");
			console.log(result);
			$scope.totalCount();
		});
    	console.log($scope.secondaryEvaluation);
    	console.log(vm.bidders);
    	
    	console.log($rootScope.score);
    	console.log(vm.score);
    	console.log($scope.score);

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        function loadAll () {
            SecondaryEvaluation.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
                totalAvgScore();
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.secondaryEvaluations = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
        
        $scope.total=0;
        $scope.count=0;
        
        
       // console.log(totalAvgScore());
        console.log(vm.secondaryEvaluations);
      $scope.totalCount = function(){
    	  
    	  for(var i=0;i < vm.secondaryEvaluations.length ; i++)
          {
          	if(vm.secondaryEvaluations[i].score > vm.bidders[0].minimumScoreForEligibility)
    		  
          	{
          		vm.secondaryEvaluations[i].eligible="yes";
          		console.log("It was a YES" + vm.secondaryEvaluations[i]);
          		
          	}else{
          		vm.secondaryEvaluations[i].eligible="no";
          		console.log("It was a NO");
          	}
          		}
      }
        
        /*var average=total/count;
        
        vm.secondaryEvaluations.forEach( function (arrayItem)
            	{
        	if(vm.secondaryEvaluations[i].score>average){
        		vm.secondaryEvaluations[i].eligible="yes";
        		console.log("It was a YES" + vm.secondaryEvaluations[i]);
        		
        	}else{
        		vm.secondaryEvaluations[i].eligible="no";
        		console.log("It was a NO");
        	}
        		});
        for(var i=0; i<vm.secondaryEvaluations.length; i++) {
        	if(vm.secondaryEvaluations[i].score!="null" && vm.secondaryEvaluations[i].score!=undefined)
        		{
        	      total+=vm.secondaryEvaluations[i].score;
        	      console.log(total);
        	      count++;
        	      console.log(count);
        		}
        }
        
        
        var average=total/count;
        for(i=0; i<vm.secondaryEvaluations.length; i++){
        	if(vm.secondaryEvaluations[i].score>average){
        		vm.secondaryEvaluations[i].eligible="yes";
        		console.log("It was a YES" + vm.secondaryEvaluations[i]);
        		
        	}else{
        		vm.secondaryEvaluations[i].eligible="no";
        		console.log("It was a NO");
        	}
        	
	
        } */
        
        $scope.showFee = function(userId) {
        	
        	
        	console.log(vm.bidders + "hey user");

            $scope.msg = 'clicked';
          } 
    }
})();
