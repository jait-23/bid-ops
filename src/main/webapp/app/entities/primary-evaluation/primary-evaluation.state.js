(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('primary-evaluation', {
            parent: 'entity',
            url: '/primary-evaluation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.primaryEvaluation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/primary-evaluation/primary-evaluations.html',
                    controller: 'PrimaryEvaluationController',
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
                    $translatePartialLoader.addPart('primaryEvaluation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('primary-evaluation-detail', {
            parent: 'primary-evaluation',
            url: '/primary-evaluation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.primaryEvaluation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/primary-evaluation/primary-evaluation-detail.html',
                    controller: 'PrimaryEvaluationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('primaryEvaluation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PrimaryEvaluation', function($stateParams, PrimaryEvaluation) {
                    return PrimaryEvaluation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'primary-evaluation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('primary-evaluation-detail.edit', {
            parent: 'primary-evaluation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/primary-evaluation/primary-evaluation-dialog.html',
                    controller: 'PrimaryEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrimaryEvaluation', function(PrimaryEvaluation) {
                            return PrimaryEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('primary-evaluation.new', {
            parent: 'primary-evaluation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/primary-evaluation/primary-evaluation-dialog.html',
                    controller: 'PrimaryEvaluationDialogController',
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
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('primary-evaluation', null, { reload: 'primary-evaluation' });
                }, function() {
                    $state.go('primary-evaluation');
                });
            }]
        })
        .state('primary-evaluation.edit', {
            parent: 'primary-evaluation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/primary-evaluation/primary-evaluation-dialog.html',
                    controller: 'PrimaryEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrimaryEvaluation', function(PrimaryEvaluation) {
                            return PrimaryEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('primary-evaluation', null, { reload: 'primary-evaluation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('primary-evaluation.delete', {
            parent: 'primary-evaluation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/primary-evaluation/primary-evaluation-delete-dialog.html',
                    controller: 'PrimaryEvaluationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PrimaryEvaluation', function(PrimaryEvaluation) {
                            return PrimaryEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('primary-evaluation', null, { reload: 'primary-evaluation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
