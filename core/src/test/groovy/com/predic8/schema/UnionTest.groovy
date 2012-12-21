/* Copyright 2012 predic8 GmbH, www.predic8.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

package com.predic8.schema

import com.predic8.xml.util.*
import com.predic8.schema.creator.*
import com.predic8.wstool.creator.*
import groovy.xml.MarkupBuilder

class UnionTest extends GroovyTestCase{

  def schema

  void setUp() {
    def parser = new SchemaParser(resourceResolver: new ClasspathResolver())
    schema = parser.parse(input:"/schema/union.xsd")
  }

  void testParser(){
    assertEquals(3, schema.getType('contact').union.memberTypes.size())
    assertEquals(2, schema.getType('fax').union.simpleTypes.size())
  }

  void testSchemaCreator(){
    def strWriter = new StringWriter()
    def creator = new SchemaCreator(builder : new MarkupBuilder(strWriter))
    schema.create(creator, new SchemaCreatorContext())
    def testSchema = new XmlSlurper().parseText(strWriter.toString())
    assertEquals(2, testSchema.simpleType[2].union.simpleType.size())
    assertEquals("xsd:string tns:email tns:fax", testSchema.simpleType[0].union.@memberTypes.toString())
  }
}

