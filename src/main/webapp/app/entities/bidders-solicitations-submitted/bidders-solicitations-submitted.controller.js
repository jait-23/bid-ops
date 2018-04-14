(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('BiddersSolicitationsSubmittedDialogController', BiddersSolicitationsSubmittedDialogController);

    BiddersSolicitationsSubmittedDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BiddersSolicitationsSubmitted'];

    function BiddersSolicitationsSubmittedDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BiddersSolicitationsSubmitted) {
        var vm = this;

        vm.biddersSolicitationsSubmitted = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.biddersSolicitationsSubmitted.id !== null) {
                BiddersSolicitationsSubmitted.update(vm.biddersSolicitationsSubmitted, onSaveSuccess, onSaveError);
            } else {
                BiddersSolicitationsSubmitted.save(vm.biddersSolicitationsSubmitted, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bidopscoreApp:biddersSolicitationsSubmittedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();