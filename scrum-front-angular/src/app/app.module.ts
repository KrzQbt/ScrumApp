import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {DatePipe} from '@angular/common';
import { GoogleChartsModule } from 'angular-google-charts';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatSidenavModule} from '@angular/material/sidenav';
import {MatFormFieldModule} from '@angular/material/form-field';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input'; 
import {MatDatepickerModule} from '@angular/material/datepicker';



import { BacklogGroomComponent } from './backlog/backlog-groom/backlog-groom.component';

import {MatDialogModule} from '@angular/material/dialog';
import { BacklogEditDialogComponent } from './backlog/backlog-edit-dialog/backlog-edit-dialog.component';
import { BoardComponent } from './scrum/board/board.component';
import { BoardRowComponent } from './scrum/board-row/board-row.component';
import { ProjectListingComponent } from './project/project-listing/project-listing.component';
import { ProjectDetailComponent } from './project/project-detail/project-detail.component';
import { ProjectBacklogListingComponent } from './project/project-backlog-listing/project-backlog-listing.component';
import { ProjectDetailInfoComponent } from './project/project-detail-info/project-detail-info.component';
import { BacklogDetailComponent } from './backlog/backlog-detail/backlog-detail.component';
import { BacklogItemComponent } from './backlog/backlog-item/backlog-item.component';
import { BacklogOverviewComponent } from './backlog/backlog-overview/backlog-overview.component';
import { LoginPageComponent } from './authentication/login-page/login-page.component';
import { authInterceptorProviders } from './services/auth-interceptor/auth.interceptor';
import { RegisterPageComponent } from './authentication/register-page/register-page.component';
import { UserPageComponent } from './user/user-page/user-page.component';
import { SprintDashboardComponent } from './sprint/sprint-dashboard/sprint-dashboard.component';

import { SprintTimePreferenceFormComponent } from './sprint/sprint-time-preference-form/sprint-time-preference-form.component';
import { ProjectDashboardComponent } from './project/project-dashboard/project-dashboard.component';
import { SprintBoardComponent } from './sprint/sprint-board/sprint-board.component';
import { SprintTaskCreatorComponent } from './sprint/sprint-task-creator/sprint-task-creator.component';
import { StoryToItemComponent } from './backlog/story-to-item/story-to-item.component';
import { SprintItemPickComponent } from './sprint/sprint-item-pick/sprint-item-pick.component';
import { SprintTimePreferenceReportComponent } from './sprint/sprint-time-preference-report/sprint-time-preference-report.component';
import { ProjectSprintListingComponent } from './project/project-sprint-listing/project-sprint-listing.component';
import { SprintBoardRowComponent } from './sprint/sprint-board-row/sprint-board-row.component';
import { TimeReportSiteComponent } from './time/time-report-site/time-report-site.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ProjectParticipationComponent } from './user-management/project-participation/project-participation.component';
import { SprintParticipationComponent } from './user-management/sprint-participation/sprint-participation.component';
import { TimeSubmitComponent } from './time-report/time-submit/time-submit.component';
import { TimeReportComponent } from './time-report/time-report/time-report.component';
import { SprintBurndownComponent } from './sprint/sprint-burndown/sprint-burndown.component';
import { MainDashboardComponent } from './main/main-dashboard/main-dashboard.component';
import { ProjectCreationComponent } from './main/project-creation/project-creation.component';
import { StoriesCrudComponent } from './stories/stories-crud/stories-crud.component';
import { BacklogCreationComponent } from './backlog/backlog-creation/backlog-creation.component';
import { BacklogItemCreationComponent } from './backlog/backlog-item-creation/backlog-item-creation.component';
import { SprintCreationComponent } from './sprint/sprint-creation/sprint-creation.component';
import { SprintManagerComponent } from './sprint/sprint-manager/sprint-manager.component';
import { ProjectManagerComponent } from './project/project-manager/project-manager.component';
import { UrlBreadcrumpComponent } from './util/url-breadcrump/url-breadcrump.component';
import { MySprintListingComponent } from './sprint/my-sprint-listing/my-sprint-listing.component';
import { SprintListComponent } from './sprint/sprint-list/sprint-list.component';



@NgModule({
  declarations: [
    AppComponent,
    BacklogGroomComponent,
    BacklogEditDialogComponent,
    BoardComponent,
    BoardRowComponent,
    ProjectListingComponent,
    ProjectDetailComponent,
    ProjectBacklogListingComponent,
    ProjectDetailInfoComponent,
    BacklogDetailComponent,
    BacklogItemComponent,
    BacklogOverviewComponent,
    LoginPageComponent,
    RegisterPageComponent,
    UserPageComponent,
    SprintDashboardComponent,
    
    SprintTimePreferenceFormComponent,
    ProjectDashboardComponent,
    SprintBoardComponent,
    SprintTaskCreatorComponent,
    StoryToItemComponent,
    SprintItemPickComponent,
    SprintTimePreferenceReportComponent,
    ProjectSprintListingComponent,
    SprintBoardRowComponent,
    TimeReportSiteComponent,
    ProjectParticipationComponent,
    SprintParticipationComponent,
    TimeSubmitComponent,
    TimeReportComponent,
    SprintBurndownComponent,
    MainDashboardComponent,
    ProjectCreationComponent,
    StoriesCrudComponent,
    BacklogCreationComponent,
    BacklogItemCreationComponent,
    SprintCreationComponent,
    SprintManagerComponent,
    ProjectManagerComponent,
    UrlBreadcrumpComponent,
    MySprintListingComponent,
    SprintListComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatFormFieldModule,
    DragDropModule,
    MatSelectModule,
    MatInputModule,
    MatDialogModule,
    MatDatepickerModule,
    GoogleChartsModule
    
  ],
  providers: [authInterceptorProviders,DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
