package doonv.jettop.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GradesRequest(
    val weekIndex: Int = 0,
    val viewType: Int = 0,
    /** Required. Sends empty grade list when absent. */
    val studyYear: Int,
    val studyYearName: String? = null,
    /** Required. */
    val studentID: String,
    val studentName: String? = null,
    val classCode: Int? = null,
    /** Filters the grades to a specific semester. */
    val periodID: Int? = null,
    val periodName: String? = null,
    /** Required. */
    val moduleID: Int
)

@Serializable
data class Grade(
    val evaluationID: Int? = null,
    val title: String? = null,
    val date: LocalDateTime? = null,
    val type: String? = null,
    val typeCode: Int? = null,
    val teacherFirstName: String,
    val teacherLastName: String,
    val grade: Double? = null,
    //val gradeTranslation: null,
    val subject: String? = null,
    val level: String? = null,
    val weight: Double? = null,
    //val remark: null,
    //val assessment: null  ,
    //val assessmentId: null,
    //val components: [],
    //val componentComment: null,
    //val componentAssement: null,
    //val reExaminationRequestLastDate: null,
    //val reExaminationDate: null,
    //val reExaminationNote: null,
    //val isDeleted: 0,
    //val period_id: 0,
    //val componentsWeightInPer: true,
    //val useTomax: false,
    //val tomaxTask: false,
    //val tomaxVix: null,
    //val tomaxGrades: null
)
