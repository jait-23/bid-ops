(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SolicitationsDetail1Controller', SolicitationsDetail1Controller);

    SolicitationsDetail1Controller.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Solicitations'];

    function SolicitationsDetail1Controller($scope, $rootScope, $stateParams, previousState, entity, Solicitations) {
        var vm = this;

        vm.solicitations = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bidopscoreApp:solicitationsUpdate', function(event, result) {
            vm.solicitations = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        $scope.tabs = [{
            title: 'Details',
            url: 'details.tpl.html'
        }, {
            title: 'Extra Info',
            url: 'extraInfo.tpl.html'
        }];

    $scope.currentTab = 'details.tpl.html';

    $scope.onClickTab = function (tab) {
        $scope.currentTab = tab.url;
    }
    
    $scope.isActiveTab = function(tabUrl) {
        return tabUrl == $scope.currentTab;
    }

    
    }
})();
