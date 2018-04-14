(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .controller('BiddersSolicitationsSubmittedDeleteController',BiddersSolicitationsSubmittedDeleteController);

    BiddersSolicitationsSubmittedDeleteController.$inject = ['$uibModalInstance', 'entity', 'BiddersSolicitationsSubmitted'];

    function BiddersSolicitationsSubmittedDeleteController($uibModalInstance, entity, BiddersSolicitationsSubmitted) {
        var vm = this;

        vm.biddersSolicitationsSubmitted = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BiddersSolicitationsSubmitted.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();