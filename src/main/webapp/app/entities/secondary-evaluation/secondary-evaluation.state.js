(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('secondary-evaluation', {
            parent: 'entity',
            url: '/secondary-evaluation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.secondaryEvaluation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/secondary-evaluation/secondary-evaluations.html',
                    controller: 'SecondaryEvaluationController',
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
                    $translatePartialLoader.addPart('secondaryEvaluation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('minimum-score-criteria', {
            parent: 'entity',
            url: '/minimum-score-criteria',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.secondaryEvaluation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/secondary-evaluation/minimum-score-criteria.html',
                    controller: 'MinimumScoreCriteriaController',
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
                    $translatePartialLoader.addPart('secondaryEvaluation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('calculate-fee', {
            parent: 'entity',
            url: '/calculate-fee',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.secondaryEvaluation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/secondary-evaluation/calculate-fee.html',
                    controller: 'CalculateFeeController',
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
                    $translatePartialLoader.addPart('secondaryEvaluation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('secondary-evaluation-detail', {
            parent: 'secondary-evaluation',
            url: '/secondary-evaluation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.secondaryEvaluation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/secondary-evaluation/secondary-evaluation-detail.html',
                    controller: 'SecondaryEvaluationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('secondaryEvaluation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SecondaryEvaluation', function($stateParams, SecondaryEvaluation) {
                    return SecondaryEvaluation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'secondary-evaluation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('secondary-evaluation-detail.edit', {
            parent: 'secondary-evaluation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secondary-evaluation/secondary-evaluation-dialog.html',
                    controller: 'SecondaryEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SecondaryEvaluation', function(SecondaryEvaluation) {
                            return SecondaryEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('secondary-evaluation.new', {
            parent: 'secondary-evaluation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secondary-evaluation/secondary-evaluation-dialog.html',
                    controller: 'SecondaryEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                user_id: null,
                                evaluator_id: null,
                                doc_url: null,
                                solicitation_id: null,
                                score: null,
                                eligible: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('secondary-evaluation', null, { reload: 'secondary-evaluation' });
                }, function() {
                    $state.go('secondary-evaluation');
                });
            }]
        })
        .state('secondary-evaluation.edit', {
            parent: 'secondary-evaluation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secondary-evaluation/secondary-evaluation-dialog.html',
                    controller: 'SecondaryEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SecondaryEvaluation', function(SecondaryEvaluation) {
                            return SecondaryEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('secondary-evaluation', null, { reload: 'secondary-evaluation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('secondary-evaluation.delete', {
            parent: 'secondary-evaluation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secondary-evaluation/secondary-evaluation-delete-dialog.html',
                    controller: 'SecondaryEvaluationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SecondaryEvaluation', function(SecondaryEvaluation) {
                            return SecondaryEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('secondary-evaluation', null, { reload: 'secondary-evaluation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
