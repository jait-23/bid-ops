(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bidders-solicitations-submitted', {
            parent: 'entity',
            url: '/bidders-solicitations-submitted?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.biddersSolicitationsSubmitted.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bidders-solicitations-submitted/bidders-solicitations-submitteds.html',
                    controller: 'BiddersSolicitationsSubmittedController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('biddersSolicitationsSubmitted');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bidders-solicitations-submitted-detail', {
            parent: 'bidders-solicitations-submitted',
            url: '/bidders-solicitations-submitted/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.biddersSolicitationsSubmitted.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bidders-solicitations-submitted/bidders-solicitations-submitted-detail.html',
                    controller: 'BiddersSolicitationsSubmittedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('biddersSolicitationsSubmitted');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BiddersSolicitationsSubmitted', function($stateParams, BiddersSolicitationsSubmitted) {
                    return BiddersSolicitationsSubmitted.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bidders-solicitations-submitted',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bidders-solicitations-submitted-detail.edit', {
            parent: 'bidders-solicitations-submitted-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bidders-solicitations-submitted/bidders-solicitations-submitted-dialog.html',
                    controller: 'BiddersSolicitationsSubmittedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BiddersSolicitationsSubmitted', function(BiddersSolicitationsSubmitted) {
                            return BiddersSolicitationsSubmitted.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bidders-solicitations-submitted.new', {
            parent: 'bidders-solicitations-submitted',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bidders-solicitations-submitted/bidders-solicitations-submitted-dialog.html',
                    controller: 'BiddersSolicitationsSubmittedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                solicitationId: null,
                                docURL: null,
                                categorySolicitations: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bidders-solicitations-submitted', null, { reload: 'bidders-solicitations-submitted' });
                }, function() {
                    $state.go('bidders-solicitations-submitted');
                });
            }]
        })
        .state('bidders-solicitations-submitted.edit', {
            parent: 'bidders-solicitations-submitted',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bidders-solicitations-submitted/bidders-solicitations-submitted-dialog.html',
                    controller: 'BiddersSolicitationsSubmittedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BiddersSolicitationsSubmitted', function(BiddersSolicitationsSubmitted) {
                            return BiddersSolicitationsSubmitted.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bidders-solicitations-submitted', null, { reload: 'bidders-solicitations-submitted' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bidders-solicitations-submitted.delete', {
            parent: 'bidders-solicitations-submitted',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bidders-solicitations-submitted/bidders-solicitations-submitted-delete-dialog.html',
                    controller: 'BiddersSolicitationsSubmittedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BiddersSolicitationsSubmitted', function(BiddersSolicitationsSubmitted) {
                            return BiddersSolicitationsSubmitted.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bidders-solicitations-submitted', null, { reload: 'bidders-solicitations-submitted' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();