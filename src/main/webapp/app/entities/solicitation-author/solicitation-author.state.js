(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('solicitation-author', {
            parent: 'entity',
            url: '/solicitation-author?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.solicitationAuthor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solicitation-author/solicitation-authors.html',
                    controller: 'SolicitationAuthorController',
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
                    $translatePartialLoader.addPart('solicitationAuthor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('solicitation-author-detail', {
            parent: 'solicitation-author',
            url: '/solicitation-author/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.solicitationAuthor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solicitation-author/solicitation-author-detail.html',
                    controller: 'SolicitationAuthorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('solicitationAuthor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SolicitationAuthor', function($stateParams, SolicitationAuthor) {
                    return SolicitationAuthor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'solicitation-author',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('solicitation-author-detail.edit', {
            parent: 'solicitation-author-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitation-author/solicitation-author-dialog.html',
                    controller: 'SolicitationAuthorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SolicitationAuthor', function(SolicitationAuthor) {
                            return SolicitationAuthor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solicitation-author.new', {
            parent: 'solicitation-author',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitation-author/solicitation-author-dialog.html',
                    controller: 'SolicitationAuthorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                authorId: null,
                                solicitationId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('solicitation-author', null, { reload: 'solicitation-author' });
                }, function() {
                    $state.go('solicitation-author');
                });
            }]
        })
        .state('solicitation-author.edit', {
            parent: 'solicitation-author',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitation-author/solicitation-author-dialog.html',
                    controller: 'SolicitationAuthorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SolicitationAuthor', function(SolicitationAuthor) {
                            return SolicitationAuthor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solicitation-author', null, { reload: 'solicitation-author' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solicitation-author.delete', {
            parent: 'solicitation-author',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitation-author/solicitation-author-delete-dialog.html',
                    controller: 'SolicitationAuthorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SolicitationAuthor', function(SolicitationAuthor) {
                            return SolicitationAuthor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solicitation-author', null, { reload: 'solicitation-author' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
