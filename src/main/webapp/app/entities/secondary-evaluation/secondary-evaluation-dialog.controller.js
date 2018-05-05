(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('SecondaryEvaluationDialogController', SecondaryEvaluationDialogController);

    SecondaryEvaluationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SecondaryEvaluation'];

    function SecondaryEvaluationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SecondaryEvaluation) {
        var vm = this;

        vm.secondaryEvaluation = entity;
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
            if (vm.secondaryEvaluation.id !== null) {
                SecondaryEvaluation.update(vm.secondaryEvaluation, onSaveSuccess, onSaveError);
            } else {
                SecondaryEvaluation.save(vm.secondaryEvaluation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bidopscoreApp:secondaryEvaluationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
