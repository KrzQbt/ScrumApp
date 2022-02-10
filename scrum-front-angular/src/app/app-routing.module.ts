import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BacklogGroomComponent } from './backlog/backlog-groom/backlog-groom.component';
import { BoardComponent } from './scrum/board/board.component';
import { ProjectListingComponent } from './project/project-listing/project-listing.component';
import { ProjectDetailComponent } from './project/project-detail/project-detail.component';
import { BacklogDetailComponent } from './backlog/backlog-detail/backlog-detail.component';
import { LoginPageComponent } from './authentication/login-page/login-page.component';
import { RegisterPageComponent } from './authentication/register-page/register-page.component';
import { UserPageComponent } from './user/user-page/user-page.component';
import { SprintDashboardComponent } from './sprint/sprint-dashboard/sprint-dashboard.component';
import { SprintItemPickComponent } from './sprint/sprint-item-pick/sprint-item-pick.component';
import { SprintTaskCreatorComponent } from './sprint/sprint-task-creator/sprint-task-creator.component';
import { SprintBoardComponent } from './sprint/sprint-board/sprint-board.component';

import { TimeReportSiteComponent } from './time/time-report-site/time-report-site.component';

import { ProjectParticipationComponent } from './user-management/project-participation/project-participation.component';

import { SprintParticipationComponent } from './user-management/sprint-participation/sprint-participation.component';

import { TimeSubmitComponent } from './time-report/time-submit/time-submit.component';

import { TimeReportComponent } from './time-report/time-report/time-report.component';

import { MainDashboardComponent } from './main/main-dashboard/main-dashboard.component';
import { ProjectCreationComponent } from './main/project-creation/project-creation.component';

import { StoriesCrudComponent } from './stories/stories-crud/stories-crud.component';
import { BacklogCreationComponent } from './backlog/backlog-creation/backlog-creation.component';
import { SprintCreationComponent } from './sprint/sprint-creation/sprint-creation.component';
import { SprintManagerComponent } from './sprint/sprint-manager/sprint-manager.component';
import { ProjectManagerComponent } from './project/project-manager/project-manager.component';
import { MySprintListingComponent } from './sprint/my-sprint-listing/my-sprint-listing.component';
import { SprintListComponent } from './sprint/sprint-list/sprint-list.component';




const routes: Routes = [
  {path: 'user/:uid', component: UserPageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'register', component: RegisterPageComponent},
  {path: 'groom', component: BacklogGroomComponent},
  {path: 'board', component: BoardComponent},
  {path: 'project/new', component: ProjectCreationComponent},
  {path: 'backlog/:bid', component: BacklogDetailComponent},
  {path: 'project/:pid/stories', component: StoriesCrudComponent},
  {path: 'project/:pid/manage', component: ProjectManagerComponent},
  {path: 'project/:pid/sprint/new', component: SprintCreationComponent},
  {path: 'project/:pid/backlog/new', component: BacklogCreationComponent},
  {path: 'project/:pid/backlog/:bid', component: ProjectDetailComponent},
  {path: 'project/:pid/backlog', component: ProjectDetailComponent},
  {path: 'project/:pid/backlog', component: ProjectDetailComponent},
  {path: 'project/:pid', component: ProjectDetailComponent},
  
  {path: 'project', component: ProjectListingComponent},
  {path: 'backlog/', component: ProjectListingComponent},
  {path: 'sprint/:sid', component: SprintDashboardComponent},
  {path:'sprint/:sid/pick', component: SprintItemPickComponent},
  {path:'sprint/:sid/manage', component: SprintManagerComponent},
  {path:'sprint/:sid/task', component: SprintTaskCreatorComponent},
  {path:'sprint/:sid/board', component: SprintBoardComponent},
  {path:'sprint/:sid/time', component: TimeSubmitComponent},
  {path:'project/:pid/participation', component: ProjectParticipationComponent},
  {path:'sprint/:sid/participation', component: ProjectListingComponent},
  {path:'sprint/:sid/report', component: TimeReportComponent},
  {path:'sprint', component: SprintListComponent},
  
  {path:'main', component: MainDashboardComponent}

 ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
