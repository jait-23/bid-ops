(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('BiddersDialogController', BiddersDialogController);

    BiddersDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bidders'];

    function BiddersDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Bidders) {
        var vm = this;

        vm.bidders = entity;
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
            if (vm.bidders.id !== null) {
                Bidders.update(vm.bidders, onSaveSuccess, onSaveError);
            } else {
                Bidders.save(vm.bidders, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bidopscoreApp:biddersUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
