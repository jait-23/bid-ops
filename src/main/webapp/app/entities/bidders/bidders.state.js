(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bidders', {
            parent: 'entity',
            url: '/bidders?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.bidders.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bidders/bidders.html',
                    controller: 'BiddersController',
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
                    $translatePartialLoader.addPart('bidders');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bidders-detail', {
            parent: 'bidders',
            url: '/bidders/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.bidders.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bidders/bidders-detail.html',
                    controller: 'BiddersDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bidders');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Bidders', function($stateParams, Bidders) {
                    return Bidders.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bidders',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bidders-detail.edit', {
            parent: 'bidders-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bidders/bidders-dialog.html',
                    controller: 'BiddersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Bidders', function(Bidders) {
                            return Bidders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bidders.new', {
            parent: 'bidders',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bidders/bidders-dialog.html',
                    controller: 'BiddersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                solicitationWishlistId: null,
                                subscribedCategories: null,
                                submittedSolicitationsId: null,
                                proposedFee: null,
                                minimumScoreForEligibility: null,
                                maximumFeeScore: null,
                                feeScore: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bidders', null, { reload: 'bidders' });
                }, function() {
                    $state.go('bidders');
                });
            }]
        })
        .state('bidders.edit', {
            parent: 'bidders',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bidders/bidders-dialog.html',
                    controller: 'BiddersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Bidders', function(Bidders) {
                            return Bidders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bidders', null, { reload: 'bidders' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bidders.delete', {
            parent: 'bidders',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bidders/bidders-delete-dialog.html',
                    controller: 'BiddersDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Bidders', function(Bidders) {
                            return Bidders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bidders', null, { reload: 'bidders' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
