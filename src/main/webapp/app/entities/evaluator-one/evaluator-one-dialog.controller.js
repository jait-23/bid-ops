(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('EvaluatorOneDialogController', EvaluatorOneDialogController);

    EvaluatorOneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EvaluatorOne'];

    function EvaluatorOneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EvaluatorOne) {
        var vm = this;

        vm.evaluatorOne = entity;
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
            if (vm.evaluatorOne.id !== null) {
                EvaluatorOne.update(vm.evaluatorOne, onSaveSuccess, onSaveError);
            } else {
                EvaluatorOne.save(vm.evaluatorOne, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bidopscoreApp:evaluatorOneUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
